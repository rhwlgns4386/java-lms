package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Session {

    private final SessionImage sessionImage;
    private final SessionState sessionState;
    private final PaymentStrategy sessionStrategy;
    private final SessionDate sessionDate;

    public Session(int size, String imageType, int width, int height, SessionState sessionState, PaymentStrategy sessionStrategy, LocalDate startDate, LocalDate endDate) {
        this(new SessionImage(size, imageType, width, height), sessionState, sessionStrategy, new SessionDate(startDate, endDate));
    }

    public Session(SessionImage sessionImage, SessionState sessionState, PaymentStrategy sessionStrategy, SessionDate sessionDate) {
        this.sessionImage = sessionImage;
        this.sessionState = sessionState;
        this.sessionStrategy = sessionStrategy;
        this.sessionDate = sessionDate;
    }

    public boolean applyForCourse(Payment payment, LocalDate date) {
        if (!SessionState.isOpen(this.sessionState)) {
            return false;
        }

        if (!sessionDate.isInclude(date)) {
            return false;
        }

        if (!this.sessionStrategy.payable(payment)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return Objects.equals(sessionImage, session.sessionImage) && sessionState == session.sessionState && Objects.equals(sessionStrategy, session.sessionStrategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionImage, sessionState, sessionStrategy);
    }
}
