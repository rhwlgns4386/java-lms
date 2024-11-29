package nextstep.courses.domain;

public class Pixel {
    private final Positive value;

    public Pixel(int value) {
        this(new Positive(value));
    }

    public Pixel(Positive value) {
        this.value = value;
    }
}
