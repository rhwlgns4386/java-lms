package nextstep.courses.domain.session;

public class LimitedCapacity implements StudentCapacity {

    public static final int MIN_CAPACITY = 1;

    private final int capacity;

    public LimitedCapacity(int capacity) {
        if (capacity < MIN_CAPACITY) {
            throw new IllegalArgumentException("수강 인원은 1명 이상이어야 합니다.");
        }
        this.capacity = capacity;
    }

    @Override
    public boolean isApplicable(int count) {
        return count < capacity;
    }

}
