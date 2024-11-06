package nextstep.courses.domain;

public class CurrentEnrollment {
    private int currentStudentCount;

    public CurrentEnrollment(int currentStudentCount) {
        this.currentStudentCount = currentStudentCount;
    }

    public void addStudent(MaxEnrollment maxEnrollment) {
        if (maxEnrollment.exceeds(currentStudentCount)) {
            throw new IllegalArgumentException("현재 등록 인원은 최대 등록 인원을 초과할 수 없습니다.");
        }
        currentStudentCount++;
    }

}
