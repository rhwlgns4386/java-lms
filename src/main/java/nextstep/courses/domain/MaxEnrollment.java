package nextstep.courses.domain;

public class MaxEnrollment {
    private final int maxStudentCount;

    public MaxEnrollment(int maxStudentCount) {
        this.maxStudentCount = maxStudentCount;
    }

    public boolean exceeds(int studentCount) {
        return studentCount > maxStudentCount;
    }
}
