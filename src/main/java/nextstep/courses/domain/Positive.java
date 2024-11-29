package nextstep.courses.domain;

import java.util.Objects;
import nextstep.courses.NonPositiveException;

public class Positive {
    private final int value;

    public Positive(int value) {
        validatePositive(value);
        this.value = value;
    }

    private static void validatePositive(int value) {
        if (value < 0) {
            throw new NonPositiveException();
        }
    }

    public boolean isLessThanOrEqualTo(Positive positive) {
        return this.value <= positive.value;
    }

    public Positive multiply(Positive positive) {
        return new Positive(this.value * positive.value);
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
        return value == positive.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
