package nextstep.courses.domain;

import java.util.Objects;

public class Pixel {
    private final Positive value;

    public Pixel(int value) {
        this(new Positive(value));
    }

    public Pixel(Positive value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public boolean isLessThanOrEqualTo(Pixel pixel) {
        return this.value.isLessThanOrEqualTo(pixel.value);
    }
}
