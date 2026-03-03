package exchangetask27.dto;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class ConvertResponseDto {

    private String date;
    private String from;
    private String to;
    private BigDecimal amount;
    private BigDecimal result;

    public ConvertResponseDto(String date, String from, String to,
                              BigDecimal amount, BigDecimal result) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.result = result;
    }

    public String getDate() { return date; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getResult() { return result; }
}