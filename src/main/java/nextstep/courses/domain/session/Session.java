package nextstep.courses.domain.session;

import nextstep.courses.dto.SessionPaymentInfo;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Session {

    private static final long NOT_ASSIGNED = -1;

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
        this(NOT_ASSIGNED, name, coverImage, maxEnrollment, sessionState, sessionFee, startDate, endDate);
    }

    protected Session(Long id, String name, CoverImage coverImage, int maxEnrollment,
                      SessionState sessionState, long sessionFee, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.name = name;
        this.coverImage = coverImage;
        this.maxEnrollment = maxEnrollment;
        this.sessionFee = sessionFee;
        this.sessionState = sessionState;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public final SessionPaymentInfo tryRegisterForSession() {
        if (sessionState != SessionState.OPEN) {
            throw new IllegalStateException(sessionState.getDesc() + " 상태이기 때문에 강의 신청할 수 없습니다");
        }
        checkRegister();
        return new SessionPaymentInfo(id, sessionFee);
    }

    protected abstract void checkRegister();

    public boolean finalizeSessionRegistration(Payment payment) {
        if (payment.match(id, sessionFee)) {
            enrollment++;
            return true;
        }
        return false;
    }

    public boolean checkPaymentInfoMatch(Payment payment) {
        return payment.match(id, sessionFee);
    }

    public boolean hasSameId(Long id) {
        return this.id.equals(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
