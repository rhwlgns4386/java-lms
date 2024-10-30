package nextstep.courses.domain;

public class StudentCapacity {

    public static final int MIN_CAPACITY = 1;

    private final int capacity;
    private int count;

    public StudentCapacity(int capacity) {
        if (capacity < MIN_CAPACITY) {
            throw new IllegalArgumentException("수강 인원은 1명 이상이어야 합니다.");
        }
        this.capacity = capacity;
        this.count = 0;
    }

    public void increment() {
        if (count >= capacity) {
            throw new IllegalArgumentException("정원을 초과했습니다.");
        }
        count++;
    }

    public int getCount() {
        return count;
    }
}
