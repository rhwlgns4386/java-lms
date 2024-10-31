package nextstep.sessions.domain;

public enum PriceType {
    FREE, PAID;

    public static PriceType of(String type) {
        return PriceType.valueOf(type.toUpperCase());
    }
}
