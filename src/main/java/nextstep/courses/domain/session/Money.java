package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;

import java.util.Objects;

public class Money {
    private static final int ZERO = 0;
    private final long amount;

    public Money(Payment payment) {
        this(payment.getAmount());
    }

    public Money(int amount) {
        this((long) amount);
    }

    public Money(long amount) {
        if (amount < ZERO) {
            throw new IllegalArgumentException("금액은 " + ZERO + "보다 작을 수 없습니다.");
        }

        this.amount = amount;
    }

    public boolean isDifferent(Money other) {
        return !this.equals(other);
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount == money.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }

}
