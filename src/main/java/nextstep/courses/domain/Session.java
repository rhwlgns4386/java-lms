package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session {
    private Long sessionId;

    private SessionPeriod sessionPeriod;

    private SessionCoverImage sessionCoverImage;

    private SessionFeeType sessionFeeType;

    private Long amount;

    private Integer maxPersonnel;

    private SessionStatus sessionStatus;

    private List<Long> nsUserIds = new ArrayList<>();

    public Session(Long sessionId, SessionPeriod sessionPeriod, SessionCoverImage sessionCoverImage, SessionFeeType sessionFeeType, Long amount, Integer maxPersonnel, SessionStatus sessionStatus) {
        this.sessionId = sessionId;
        this.sessionPeriod = sessionPeriod;
        this.sessionCoverImage = sessionCoverImage;
        this.sessionFeeType = sessionFeeType;
        this.amount = amount;
        this.maxPersonnel = maxPersonnel;
        this.sessionStatus = sessionStatus;
    }

    public void add(Payment payment) {
        if (!sessionStatus.isRecruiting()) {
            throw new IllegalArgumentException("Session is not recruiting.");
        }
        if (!Objects.equals(amount, payment.getAmount())) {
            throw new IllegalArgumentException("Payment amount does not match.");
        }
        if (sessionFeeType.isPaid() && sizeNsUsers() >= maxPersonnel) {
            throw new IllegalArgumentException("Max personnel exceeded.");
        }

        nsUserIds.add(payment.getNsUserId());
    }

    public int sizeNsUsers() {
        return nsUserIds.size();
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
        return Objects.equals(sessionId, session.sessionId) && Objects.equals(sessionPeriod, session.sessionPeriod) && Objects.equals(sessionCoverImage, session.sessionCoverImage) && sessionFeeType == session.sessionFeeType && Objects.equals(amount, session.amount) && Objects.equals(maxPersonnel, session.maxPersonnel) && sessionStatus == session.sessionStatus && Objects.equals(nsUserIds, session.nsUserIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, sessionPeriod, sessionCoverImage, sessionFeeType, amount, maxPersonnel, sessionStatus, nsUserIds);
    }
}
