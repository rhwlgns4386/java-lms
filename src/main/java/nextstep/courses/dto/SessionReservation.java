package nextstep.courses.dto;

public class SessionReservation {

    private final Long sessionId;
    private final long sessionFee;

    public SessionReservation(Long sessionId, long sessionFee) {
        this.sessionId = sessionId;
        this.sessionFee = sessionFee;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public long getSessionFee() {
        return sessionFee;
    }
}
