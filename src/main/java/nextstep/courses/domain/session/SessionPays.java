package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;
import nextstep.qna.SessionPayException;

import java.util.ArrayList;
import java.util.List;

public class SessionPays {

    private Long id;

    private Long sessionPay;
    private List<Payment> payments = new ArrayList<>();

    public SessionPays(Long sessionPay) {
        validateNegative(sessionPay);
        this.sessionPay = sessionPay;
    }

    public void addPayments(Payment payment) {
        validateSessionPay(payment);
        this.payments.add(payment);
    }

    private void validateNegative(Long sessionPay) {
        if (sessionPay < 0) {
            throw new SessionPayException("금액은 음수일 수 없습니다.");
        }
    }

    private void validateSessionPay(Payment payment) {

        if (!payment.isEqualsAmount(sessionPay)) {
            throw new SessionPayException("결제금액과 강의금액이 맞지 않습니다");
        }
    }
}
