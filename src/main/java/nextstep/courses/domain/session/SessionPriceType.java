package nextstep.courses.domain.session;

public enum SessionPriceType {
    PAID, FREE;

    public boolean isFree() {
        return this == FREE;
    }

}
