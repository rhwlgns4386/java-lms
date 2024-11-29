package nextstep.courses.domain;

import static nextstep.util.NullValidator.validateNull;

public class Charge {
    private final Positive value;

    public Charge(int charge) {
        this(new Positive(charge));
    }

    public Charge(Positive value) {
        validateNull(value);
        this.value = value;
    }
}
