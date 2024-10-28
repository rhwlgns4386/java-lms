package nextstep.courses.domain;

public class PaidSession extends Session {
    private SessionStatus sessionStatus;
    private Integer capacity;
    private Long price;
    private StudentManager studentManager;

    public PaidSession(SessionDate sessionDate, Image image, SessionId sessionId, Integer capacity, Long price) {
        super(sessionId, sessionDate, image, SessionType.PAID);
        this.sessionStatus = SessionStatus.PREPARING;
        this.capacity = capacity;
        this.price = price;
        this.studentManager = new StudentManager();
    }

    public SessionId getSessionId() {
        return super.getSessionId();
    }
}
