package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.util.Objects;

public class PaidPaymentStrategy implements PaymentStrategy {

    private final int price;
    private final SessionCapacity capacity;

    public PaidPaymentStrategy(int price, int capacity) {
        this(price, new SessionCapacity(capacity));
    }

    public PaidPaymentStrategy(int price, SessionCapacity capacity) {
        this.price = price;
        this.capacity = capacity;
    }

    @Override
    public boolean payable(Payment payment) {
        if (payment.isSamePrice(price)) {
            capacity.increase();
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaidPaymentStrategy)) return false;
        PaidPaymentStrategy that = (PaidPaymentStrategy) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }
}
