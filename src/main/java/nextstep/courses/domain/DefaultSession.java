package nextstep.courses.domain;

public abstract class DefaultSession {
    protected final SessionStatus status;
    protected final SessionPeriod period;

    protected DefaultSession(SessionStatus status, SessionPeriod period) {
        this.status = status;
        this.period = period;
    }

    protected abstract void register(Money amount);

    protected void validateRegisterStatus() {
        if(status.isOpen()){
            return;
        }
        throw new IllegalArgumentException("강의 상태가 모집 중일때만 수강신청이 가능합니다.");
    }
}
