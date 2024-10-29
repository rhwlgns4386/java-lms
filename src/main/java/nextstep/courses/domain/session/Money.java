package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;

import java.util.Objects;

public class Money {
    private static final int EMPTY = 0;
    private final long amount;

    public Money(Payment payment) {
        this(payment.getAmount());
    }

    public Money(int amount) {
        this((long) amount);
    }

    public Money(long amount) {
        if (amount < EMPTY) {
            throw new IllegalArgumentException("금액은 " + EMPTY + "보다 작을 수 없습니다.");
        }

        this.amount = amount;
    }

    public boolean isDifferent(Money other) {
        return !this.equals(other);
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
