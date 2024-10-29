package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;

public class PaidSession extends DefaultSession {
    private final Money courseFee;
    private SessionCapacity capacity;

    public PaidSession(SessionStatus sessionStatus, SessionPeriod period, SessionCapacity capacity, Money courseFee, CoverImage coverImage) {
        super(sessionStatus, period, coverImage);
        this.capacity = capacity;
        this.courseFee = courseFee;
    }

    @Override
    protected void register(Payment payment) {
        validateRegisterStatus();
        validateCapacity();
        validatePayment(payment);

        capacity = capacity.increase();
    }

    private void validateCapacity() {
        if (capacity.isFull()) {
            throw new IllegalArgumentException("수강 인원이 꽉 찼습니다.");
        }
    }

    private void validatePayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("결제 정보가 없습니다.");
        }
        if (courseFee.isDifferent(new Money(payment))) {
            throw new IllegalArgumentException("결제 금액이 수강료와 일치하지 않습니다");
        }
    }
}
