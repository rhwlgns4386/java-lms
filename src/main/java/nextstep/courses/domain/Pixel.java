package nextstep.courses.domain;

import nextstep.courses.NonPositiveException;

public class Pixel {
    public Pixel(int value) {
        validatePositive(value);
    }

    private static void validatePositive(int value) {
        if (value < 0) {
            throw new NonPositiveException();
        }
    }
}
