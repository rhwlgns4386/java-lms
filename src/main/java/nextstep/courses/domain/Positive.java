package nextstep.courses.domain;

import java.util.Objects;
import nextstep.courses.NonPositiveException;

public class Positive {
    private final int number;

    public Positive(int number) {
        validatePositive(number);
        this.number = number;
    }

    private static void validatePositive(int value) {
        if (value < 0) {
            throw new NonPositiveException();
        }
    }

    public boolean isLessThanOrEqualTo(Positive value) {
        return this.number <= value.number;
    }

    public boolean isLessThan(Positive value) {
        return this.number < value.number;
    }

    public Positive multiply(Positive positive) {
        return new Positive(this.number * positive.number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Positive positive = (Positive) o;
        return number == positive.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
