package nextstep.courses.domain;

import static nextstep.util.NullValidator.validateNull;

public class Capacity {
    private final Positive capacity;

    public Capacity(int capacity) {
        this(new Positive(capacity));
    }

    public Capacity(Positive capacity) {
        validateNull(capacity);
        this.capacity = capacity;
    }

    public boolean canEnroll(int size) {
        return capacity.isGraterThan(size);
    }

    public int toInt() {
        return capacity.toInt();
    }
}
