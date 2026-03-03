package exchangetask27.client;

import exchangetask27.model.Currency;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.Map;

@Component
public class CurrencyRateClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<Currency, BigDecimal> getRates(LocalDate date) {

        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        String url = "https://www.cbar.az/currencies/" + formattedDate + ".xml";

        String xml = restTemplate.getForObject(url, String.class);

        if (xml == null) {
            throw new IllegalStateException("Could not fetch currency rates");
        }

        return parseXml(xml);
    }

    private Map<Currency, BigDecimal> parseXml(String xml) {
        Map<Currency, BigDecimal> rates = new EnumMap<>(Currency.class);

        try {
            Document doc = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes()));

            NodeList valutes = doc.getElementsByTagName("Valute");

            for (int i = 0; i < valutes.getLength(); i++) {
                Element element = (Element) valutes.item(i);

                String code = element.getAttribute("Code");
                String value = element
                        .getElementsByTagName("Value")
                        .item(0)
                        .getTextContent();

                try {
                    Currency currency = Currency.from(code);
                    rates.put(currency, new BigDecimal(value));
                } catch (Exception ignored) {
                    // ignore currencies not in enum
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error parsing XML", e);
        }

        return rates;
    }
}