package nextstep.courses.domain;

import java.util.Objects;

public class Money {
    private final int amount;

    public Money(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("금액은 0보다 작을 수 없습니다.");
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
