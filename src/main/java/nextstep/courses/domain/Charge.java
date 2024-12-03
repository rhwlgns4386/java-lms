package nextstep.courses.domain;

import static nextstep.util.NullValidator.validateNull;

import java.util.Objects;

public class Charge {
    public static final Charge ZERO = new Charge(0);
    private final Positive value;

    public Charge(int charge) {
        this(new Positive(charge));
    }

    public Charge(Positive value) {
        validateNull(value);
        this.value = value;
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
        Charge charge = (Charge) o;
        return Objects.equals(value, charge.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
