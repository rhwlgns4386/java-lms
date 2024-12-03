package nextstep.courses.domain;

import static nextstep.util.NullValidator.validateNull;

import java.util.Objects;

public class Pixel {
    private final Positive value;

    public Pixel(int value) {
        this(new Positive(value));
    }

    public Pixel(Positive value) {
        validateNull(value);
        this.value = value;
    }

    public boolean isLessThanOrEqualTo(Pixel pixel) {
        return this.value.isLessThanOrEqualTo(pixel.value);
    }

    public Pixel multiply(Pixel value) {
        return new Pixel(multiply(value.value));
    }

    private Positive multiply(Positive positive) {
        return this.value.multiply(positive);
    }

    public int toInt() {
        return value.toInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pixel pixel = (Pixel) o;
        return Objects.equals(value, pixel.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
