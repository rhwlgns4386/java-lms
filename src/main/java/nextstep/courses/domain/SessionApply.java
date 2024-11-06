package nextstep.courses.domain;

public class SessionApply {
    private final Long sessionId;
    private final SessionAmount amount;
    private final int countPersonnel;

    public SessionApply(Long sessionId, SessionAmount amount, int countPersonnel) {
        this.sessionId = sessionId;
        this.amount = amount;
        this.countPersonnel = countPersonnel;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public SessionAmount getAmount() {
        return amount;
    }

    public int getCountPersonnel() {
        return countPersonnel;
    }
}
