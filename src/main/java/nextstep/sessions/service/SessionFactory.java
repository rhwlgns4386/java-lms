package nextstep.sessions.service;

import nextstep.sessions.domain.SessionFeeStatus;
import org.springframework.stereotype.Component;

@Component("sessionFactory")
public class SessionFactory {
    private final FreeSessionService freeSessionService;
    private final PaidSessionService paidSessionService;

    public SessionFactory(FreeSessionService freeSessionService, PaidSessionService paidSessionService) {
        this.freeSessionService = freeSessionService;
        this.paidSessionService = paidSessionService;
    }

    public SessionService getSessionService(SessionFeeStatus feeStatus) {
        switch (feeStatus) {
            case FREE:
                return freeSessionService;
            case PAID:
                return paidSessionService;
            default:
                throw new IllegalArgumentException(String.format("'%s'에 매칭되는 SessionService 구현체가 존재하지 않습니다.", feeStatus));
        }
    }
}
