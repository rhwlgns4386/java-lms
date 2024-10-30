package nextstep.courses.domain;

public enum Type {
    FREE,
    PAID,
    ;

    public static Type from(String stringValue) {
        return Type.valueOf(stringValue.toUpperCase());
    }

    public boolean isFree() {
        return this == FREE;
    }

    public boolean isPaid() {
        return this == PAID;
    }
}
