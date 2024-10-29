package nextstep.courses.domain;

import java.util.Objects;

public class SessionAmount {
    private final long amount;

    public SessionAmount(long amount) {
        validNegative(amount);
        this.amount = amount;
    }

    private void validNegative(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionAmount that = (SessionAmount) o;
        return Objects.equals(amount, that.amount);
    }
}
