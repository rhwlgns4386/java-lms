package nextstep.courses.dto;

public class SessionPaymentInfo {

    private final Long sessionId;
    private final long sessionFee;

    public SessionPaymentInfo(Long sessionId, long sessionFee) {
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
