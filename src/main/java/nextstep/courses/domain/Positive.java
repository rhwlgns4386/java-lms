package nextstep.courses.domain;

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
}
