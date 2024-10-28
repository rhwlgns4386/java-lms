package nextstep.courses.domain;

import java.util.Objects;

public class CourseCapacity {
    private static final int EMTY = 0;
    private final int current;
    private final int max;

    public CourseCapacity(int current, int max) {
        if (max <= EMTY) {
            throw new IllegalArgumentException("최대 수강 인원은 0보다 커야 합니다.");
        }
        if (current < EMTY) {
            throw new IllegalArgumentException("현재 수강 인원은 0보다 작을 수 없습니다.");
        }
        if (max < current) {
            throw new IllegalArgumentException("현재 수강 인원은 0보다 작을 수 없습니다.");
        }
        this.current = current;
        this.max = max;
    }

    public CourseCapacity increase() {
        if (isFull()) {
            throw new IllegalArgumentException("수강인원이 꽉 찼습니다.");
        }
        return new CourseCapacity(current + 1, max);
    }

    public boolean isFull() {
        return current >= max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseCapacity that = (CourseCapacity) o;
        return current == that.current && max == that.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(current, max);
    }
}
