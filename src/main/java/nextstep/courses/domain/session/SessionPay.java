package nextstep.courses.domain.session;

import nextstep.courses.SessionPayException;
import nextstep.payments.domain.Payment;

public class SessionPay {

    private Long sessionPay;
    private SessionPayType sessionPayType;


    public SessionPay(Long sessionPay, String payType) {

        this(sessionPay, SessionPayType.search(payType));
    }

    public SessionPay(Long sessionPay, SessionPayType sessionPayType) {
        this.sessionPay = sessionPay;
        this.sessionPayType = sessionPayType;
    }


    public boolean isPaid() {
        return sessionPayType == SessionPayType.PAID;
    }

    public void validatePay(Payment payment) {
        if (!payment.isEqualsAmount(sessionPay)) {
            throw new SessionPayException("강의금액과 결제금액이 맞지 않습니다");
        }
    }

    public Long getSessionPay() {
        return sessionPay;
    }

    public SessionPayType getSessionPayType() {
        return sessionPayType;
    }
}
