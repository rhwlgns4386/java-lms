package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class Enrollment {
    private Payment payment;

    public Enrollment(Payment payment) {
        this.payment = payment;
    }

}
