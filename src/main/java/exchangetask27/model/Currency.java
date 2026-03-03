package exchangetask27.model;

public enum Currency {
    AZN,USD,EUR,TRY,RUB;

    public static Currency from(String currency) {
        if (currency == null) {
            throw new IllegalArgumentException("currency can't be null");
        }
        try {
            return Currency.valueOf(currency.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unsupported currency: " + currency +
                            ". Allowed values: AZN, USD, EUR, TRY, RUB"
            );
        }
    }}

