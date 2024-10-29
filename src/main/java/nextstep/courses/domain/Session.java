package nextstep.courses.domain;

public class Session {
    private StudentCount studentCount;
    private Premium premium;
    private SessionState sessionState;
    private SessionDate sessionDate;
    public Session(Premium premium, int maxStudentCount, SessionDate sessionDate) {
        this(premium,maxStudentCount,SessionState.READY,sessionDate);
    }

    public Session(Premium premium, int maxStudentCount, SessionState sessionState, SessionDate sessionDate) {
        this.premium = premium;
        this.studentCount = new StudentCount(maxStudentCount);
        this.sessionState = sessionState;
        this.sessionDate = sessionDate;

    }

    public void requestSession(int requestAmount) {
        studentCount.validate(premium);
        premium.validateAmount(requestAmount);
        validateSessionState();
        studentCount.increaseCount();
    }

    private void validateSessionState() {
        if(!SessionState.isRequestSession(this.sessionState)) {
            throw new IllegalArgumentException("강의신청 기간이 아닙니다.");
        }

    }

}
