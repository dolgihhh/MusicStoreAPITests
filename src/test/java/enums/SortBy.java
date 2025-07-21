package enums;

public enum SortBy {
    PRICE_ASC("price_asc"),
    PRICE_DESC("price_desc"),
    TITLE("title"),
    YEAR("year");

    private final String value;

    SortBy(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
