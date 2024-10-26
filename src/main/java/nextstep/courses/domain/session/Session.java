package nextstep.courses.domain.session;

import nextstep.courses.dto.SessionReservation;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public abstract class Session {

    private Long id;
    private final String name;
    private final CoverImage coverImage;

    protected int enrollment;
    protected final int maxEnrollment;
    protected final long sessionFee;

    protected SessionState sessionState;

    protected LocalDateTime startDate;
    protected LocalDateTime endDate;

    protected Session(String name, CoverImage coverImage, int maxEnrollment, SessionState sessionState,
                      long sessionFee, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.coverImage = coverImage;
        this.maxEnrollment = maxEnrollment;
        this.sessionFee = sessionFee;
        this.sessionState = sessionState;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public final SessionReservation reserveSession() {
        if (sessionState != SessionState.OPEN) {
            throw new IllegalStateException(sessionState.getDesc() + " 상태이기 때문에 강의 신청할 수 없습니다");
        }
        checkRegister();
        return new SessionReservation(id, sessionFee);
    }

    public boolean completeRegister(Payment payment) {
        if (payment.match(id, sessionFee)) {
            enrollment++;
            return true;
        }
        return false;
    }

    protected abstract void checkRegister();
}
