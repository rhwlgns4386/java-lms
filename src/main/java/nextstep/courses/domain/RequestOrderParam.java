package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class RequestOrderParam {
    private Payment payment;
    private NsUser student;

    public RequestOrderParam() {
        this(new Payment(), null);
    }

    public RequestOrderParam(Payment payment) {
        this(payment, null);
    }

    public RequestOrderParam(Payment payment, NsUser student) {
        this.payment = payment;
        this.student = student;
    }

    public Payment getPayment() {
        return payment;
    }

    public NsUser getStudent() {
        return student;
    }
}
