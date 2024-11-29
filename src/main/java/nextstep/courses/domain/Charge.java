package nextstep.courses.domain;

public class Charge {
    private final Positive value;

    public Charge(int charge) {
        this(new Positive(charge));
    }

    public Charge(Positive value) {
        this.value = value;
    }
}
