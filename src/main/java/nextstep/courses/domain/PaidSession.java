package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class PaidSession extends Session {
    private SessionCapacity capacity;
    private Money fee;

    public PaidSession(Image image,
                       SessionDate sessionDate,
                       SessionId sessionId,
                       SessionCapacity capacity,
                       Money fee
    ) {
        super(image, sessionDate, sessionId, SessionStatus.PREPARING, SessionType.PAID);
        this.capacity = capacity;
        this.fee = fee;
    }

    public SessionCapacity getCapacity() {
        return capacity;
    }

    public Money getFee() {
        return fee;
    }

    @Override
    protected void register(Payment payment) {
        if (!isAvailableForRegistration()) {
            throw new IllegalStateException("Can't register session");
        }
        if (payment == null) {
            throw new IllegalArgumentException("Payment must not be null");
        }
        if (!this.fee.equals(new Money(payment.getAmount()))) {
            throw new IllegalArgumentException("amount must be equal to session fee");
        }

        this.capacity.increase();
    }
}
