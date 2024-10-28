package nextstep.courses.domain;

import java.util.Objects;

public class PaidSession extends Session {
    private SessionId sessionId;
    private SessionStatus sessionStatus;
    private Integer capacity;
    private Long price;

    public PaidSession(SessionDate sessionDate, Image image, SessionId sessionId, Integer capacity, Long price) {
        super(sessionDate, image, SessionType.PAID);
        this.sessionId = sessionId;
        this.sessionStatus = SessionStatus.PREPARING;
        this.capacity = capacity;
        this.price = price;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaidSession)) {
            return false;
        }
        PaidSession that = (PaidSession) o;
        return Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sessionId);
    }
}
