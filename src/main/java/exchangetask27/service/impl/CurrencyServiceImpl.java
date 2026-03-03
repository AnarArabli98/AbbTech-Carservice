package exchangetask27.service.impl;

import exchangetask27.dto.ConvertRequestDto;
import exchangetask27.dto.ConvertResponseDto;
import exchangetask27.model.Currency;
import exchangetask27.client.CurrencyRateClient;
import exchangetask27.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRateClient currencyRateClient;

    public CurrencyServiceImpl(CurrencyRateClient currencyRateClient) {
        this.currencyRateClient = currencyRateClient;
    }

    @Override
    public ConvertResponseDto convert(ConvertRequestDto request) {

        LocalDate date;
        try {
            date = LocalDate.parse(
                    request.getDate(),
                    DateTimeFormatter.ofPattern("dd.MM.yyyy")
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid date format. Expected dd.MM.yyyy"
            );
        }

        Currency fromCurrency = Currency.from(request.getFrom());
        Currency toCurrency = Currency.from(request.getTo());

        BigDecimal amount = request.getAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than zero"
            );
        }

        Map<Currency, BigDecimal> rates =
                currencyRateClient.getRates(date);

        BigDecimal fromRate =
                fromCurrency == Currency.AZN
                        ? BigDecimal.ONE
                        : rates.get(fromCurrency);

        BigDecimal toRate =
                toCurrency == Currency.AZN
                        ? BigDecimal.ONE
                        : rates.get(toCurrency);

        if (fromRate == null || toRate == null) {
            throw new IllegalArgumentException(
                    "Currency rate not found for given date"
            );
        }

        BigDecimal amountInAZN =
                amount.multiply(fromRate);

        BigDecimal result =
                amountInAZN.divide(toRate, 4, RoundingMode.HALF_UP);

        return new ConvertResponseDto(
                request.getDate(),
                fromCurrency.name(),
                toCurrency.name(),
                amount,
                result
        );
    }
}