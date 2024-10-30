package nextstep.sessions.domain;

public class MaximumEnrollment {
    private final int value;

    public MaximumEnrollment(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isOver(int count) {
        return value <= count;
    }
}
