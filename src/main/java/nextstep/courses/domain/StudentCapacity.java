package nextstep.courses.domain;

public class StudentCapacity {

    private final int capacity;

    public StudentCapacity(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("수강 인원은 1명 이상이어야 합니다.");
        }
        this.capacity = capacity;
    }
}
