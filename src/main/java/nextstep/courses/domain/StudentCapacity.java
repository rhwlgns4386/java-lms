package nextstep.courses.domain;

public class StudentCapacity {

    private final int capacity;
    private int count;

    public StudentCapacity(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("수강 인원은 1명 이상이어야 합니다.");
        }
        this.capacity = capacity;
    }

    public void increment() {
        if (count >= capacity) {
            throw new IllegalArgumentException("정원을 초과했습니다.");
        }
        count++;
    }
}
