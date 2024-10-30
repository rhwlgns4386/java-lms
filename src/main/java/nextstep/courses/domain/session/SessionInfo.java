package nextstep.courses.domain.session;

import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

public class SessionInfo {

    private final String title;
    private final CoverImage coverImage;
    private final long sessionFee;
    private SessionState sessionState;

    public SessionInfo(String title, CoverImage coverImage, long sessionFee) {
        this(title, coverImage, sessionFee, SessionState.PREPARING);
    }

    public SessionInfo(String title, CoverImage coverImage, long sessionFee, SessionState sessionState) {
        this.title = title;
        this.coverImage = coverImage;
        this.sessionFee = sessionFee;
        this.sessionState = sessionState;
    }

    public void checkIsOpenSession() {
        if (!sessionState.canRegister()) {
            throw new IllegalStateException(sessionState.getDesc() + " 상태이기 때문에 강의 신청할 수 없습니다");
        }
    }

    public boolean isValidPayment(Payment payment, Long sessionId) {
        return payment.isValidPayment(sessionId, sessionFee);
    }
}
