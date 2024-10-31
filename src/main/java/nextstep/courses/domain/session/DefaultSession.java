package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;

public abstract class DefaultSession {
    protected final SessionStatus status;
    protected final SessionPeriod period;
    protected final CoverImage coverImage;

    protected DefaultSession(SessionStatus status, SessionPeriod period, CoverImage coverImage) {
        this.status = status;
        this.period = period;
        this.coverImage = coverImage;
    }

    public void register(Payment payment) {
        validate(payment);
        doRegister(payment);
    }

    protected abstract void doRegister(Payment payment);
    protected abstract void validate(Payment payment);

    protected void validateSessionStatus() {
        if(status.isOpen()){
            return;
        }
        throw new IllegalArgumentException("강의 상태가 모집 중일때만 수강신청이 가능합니다.");
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public SessionPeriod getPeriod() {
        return period;
    }

    public SessionStatus getStatus() {
        return status;
    }
}
