package nextstep.courses.domain;

public class Session {
    private final SessionStatus status;
    private final SessionPeriod period;

    public Session(SessionStatus sessionStatus, SessionPeriod period) {
        this.status = sessionStatus;
        this.period = period;
    }

    public void register() {
        if (status.isOpen()) {
            return;
        }
        throw new IllegalArgumentException("강의상태가 모집 중일때만 수강신청이 가능합니다.");
    }

}
