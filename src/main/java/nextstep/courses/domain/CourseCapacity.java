package nextstep.courses.domain;

public class CourseCapacity {
    private final int current;
    private final int max;

    public CourseCapacity(int current, int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("최대 수강 인원은 0보다 커야 합니다.");
        }
        if (current < 0) {
            throw new IllegalArgumentException("현재 수강 인원은 0보다 작을 수 없습니다.");
        }
        this.current = current;
        this.max = max;

    }
}
