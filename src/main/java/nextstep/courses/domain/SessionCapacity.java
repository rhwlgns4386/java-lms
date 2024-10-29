package nextstep.courses.domain;

import nextstep.courses.exception.CannotIncreaseException;

import java.util.Objects;

public class SessionCapacity {

    public static final String MIN_CAPACITY_EXCEPT_MESSAGE = "수강인원은 최소 0명 이상이어야 합니다!";
    public static final int MIN_CAPACITY = 0;
    public static final String MAX_CAPACITY_EXCEPT_MESSAGE = "수강 제한 인원을 초과 할수 없습니다!";

    private int capacity;
    private final int maxCapacity;

    public SessionCapacity(int maxCapacity) {
        this(0, maxCapacity);
    }

    public SessionCapacity(int capacity, int maxCapacity) {
        if (maxCapacity < MIN_CAPACITY) {
            throw new IllegalArgumentException(MIN_CAPACITY_EXCEPT_MESSAGE);
        }
        this.capacity = capacity;
        this.maxCapacity = maxCapacity;
    }

    public void increase() {
        if (capacity >= maxCapacity) {
            throw new CannotIncreaseException(MAX_CAPACITY_EXCEPT_MESSAGE);
        }
        this.capacity++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SessionCapacity)) return false;
        SessionCapacity that = (SessionCapacity) o;
        return capacity == that.capacity && maxCapacity == that.maxCapacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity, maxCapacity);
    }
}
