package nextstep.courses.domain;

public class Session {
    private StudentCount studentCount;
    private Premium premium;


    public Session(Premium premium, int maxStudentCount) {
        this.premium = premium;
        this.studentCount = new StudentCount(maxStudentCount);
    }

    public void requestSession(int requestAmount) {
        studentCount.validate(premium);
        premium.validateAmount(requestAmount);
        studentCount.increaseCount();
    }

}
