package nextstep.courses.domain;

public class StudentCount {
    private int maxStudentCount;
    private int currentStudentCount = 0;

    public StudentCount(int maxStudentCount) {
        this.maxStudentCount =maxStudentCount;
    }

    //TODO 유로시 최대수강인원 체크
    public void increaseCount(Premium premium) {
        validateCount(premium);
        currentStudentCount++;
    }

    public void validateCount(Premium premium) {
        if (premium.isPremium() && currentStudentCount >= maxStudentCount) {
            throw new IllegalArgumentException("수강인원 초과");
        }
    }

}

