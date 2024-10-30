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

    protected abstract void register(Payment payment);

    //템플릿의 역할이 조금 애매한 것 같은데요!
    //이렇게 되면 DefaultSession을 확장한 클래스에서 이 메서드를 사용 안하면 해당 유효성은 빠질수가 있어서 의도하신것과 다르게 동작 하게 될 수도 있을 것 같아요!
    //register를 public으로 하나 따로 빼고 현재 메서드를 템플릿 메서드로 두면서 유효성 검사는 이 클래스에서 진행하면 어떨까요 ?
    protected void validateRegisterStatus() {
        if(status.isOpen()){
            return;
        }
        throw new IllegalArgumentException("강의 상태가 모집 중일때만 수강신청이 가능합니다.");
    }
}
