package nextstep.courses.domain;

public class Session {
    private StudentCount studentCount;
    private Premium premium;

    public Session(boolean isPremium, int maxStudentCount) {
        this.premium = new Premium(isPremium);
        this.studentCount = new StudentCount(maxStudentCount);
    }

    public void requestSession() {
        studentCount.increaseCount(premium);
    }

}
