package nextstep.courses.domain;

import java.util.Objects;

public class PositiveNumber {
    private final int num;

    public PositiveNumber(int num) {
        validateNum(num);
        this.num = num;
    }

    private static void validateNum(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("음수를 허용하지 않습니다.");
        }
    }

    public PositiveNumber increase() {
        return new PositiveNumber(num + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PositiveNumber that = (PositiveNumber) o;
        return num == that.num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num);
    }
}
