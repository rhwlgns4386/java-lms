package nextstep.courses.domain;

public class Session {

    private final Charge charge;

    public Session(Charge charge) {
        this.charge = charge;
    }

    public void enrollment(Charge fee) {
        validateCharge(fee);
    }

    private void validateCharge(Charge fee) {
        if (!this.charge.equals(fee)) {
            throw new IllegalArgumentException("강의 금액과 일치하지 않습니다");
        }
    }
}
