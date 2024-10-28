package nextstep.courses.domain;

public enum SessionFeeType {
    FREE, PAID;

    public boolean isPaid() {
        return this == PAID;
    }
}
