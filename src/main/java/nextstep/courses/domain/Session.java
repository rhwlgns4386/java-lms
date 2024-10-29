package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public abstract class Session {
    private SessionId sessionId;
    private SessionDate sessionDate;
    private Image image;
    private SessionType sessionType;
    private SessionStatus sessionStatus;

    public Session(Image image, SessionDate sessionDate, SessionId sessionId, SessionStatus sessionStatus, SessionType sessionType) {
        this.image = image;
        this.sessionDate = sessionDate;
        this.sessionId = sessionId;
        this.sessionStatus = sessionStatus;
        this.sessionType = sessionType;
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    protected abstract void register(Payment payment);

    protected boolean isAvailableForRegistration() {
        return this.sessionStatus.isOpen();
    }
}
