package nextstep.courses.domain;

import java.util.Objects;

public class Charge {
    private final Positive value;

    public Charge(int charge) {
        this(new Positive(charge));
    }

    public Charge(Positive value) {
        Objects.requireNonNull(value);
        this.value = value;
    }
}
