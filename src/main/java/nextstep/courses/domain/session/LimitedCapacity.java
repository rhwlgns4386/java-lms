package nextstep.courses.domain.session;

public class LimitedCapacity implements StudentCapacity {

    public static final int MIN_CAPACITY = 1;

    private final int maxCapacity;

    public LimitedCapacity(int maxCapacity) {
        if (maxCapacity < MIN_CAPACITY) {
            throw new IllegalArgumentException("최대 수강 인원은 1명 이상이어야 합니다.");
        }
        this.maxCapacity = maxCapacity;
    }

    @Override
    public boolean isApplicable(int enrollStudentCount) {
        return enrollStudentCount < maxCapacity;
    }

}
