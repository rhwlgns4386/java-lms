package nextstep.courses.domain;

public class StudentCount {
    private static final int DEFAULT_CURRENT_COUNT = 0;
    private int maxStudentCount;
    private int currentStudentCount;

    public StudentCount(int maxStudentCount) {
        this.maxStudentCount = maxStudentCount;
    }

    //TODO 유로시 최대수강인원 체크
    public void increaseCount() {
        currentStudentCount++;
    }

    public void validate(Premium premium) {
        if (premium.isPremium() && currentStudentCount >= maxStudentCount) {
            throw new IllegalArgumentException("수강인원 초과");
        }
    }

}

