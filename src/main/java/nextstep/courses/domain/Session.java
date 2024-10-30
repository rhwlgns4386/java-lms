package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session {
    private final Long id;

    private final SessionPeriod period;

    private final SessionCoverImage coverImage;

    private final SessionFeeType feeType;

    private final SessionAmount amount;

    private final int maxPersonnel;

    private final SessionStatus status;

    private final List<Long> nsUserIds = new ArrayList<>();

    protected Session(Long id, SessionPeriod period, SessionCoverImage coverImage, SessionFeeType feeType, SessionAmount amount, int maxPersonnel, SessionStatus status) {
        this.id = id;
        this.period = period;
        this.coverImage = coverImage;
        this.feeType = feeType;
        this.amount = amount;
        this.maxPersonnel = maxPersonnel;
        this.status = status;
    }

    public static Session createPaidSession(Long id, SessionPeriod period, SessionCoverImage coverImage, SessionAmount amount, int maxPersonnel, SessionStatus status) {
        validPaidSessionAmount(amount);
        return new Session(id, period, coverImage, SessionFeeType.PAID, amount, maxPersonnel, status);
    }

    private static void validPaidSessionAmount(SessionAmount amount) {
        if (Objects.equals(amount, new SessionAmount(0))) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    public static Session createFreeSession(Long id, SessionPeriod period, SessionCoverImage coverImage, SessionStatus status) {
        return new Session(id, period, coverImage, SessionFeeType.FREE, new SessionAmount(0L), 0, status);
    }

    private void validMaxPersonnel() {
        if (feeType.isPaid() && sizeNsUsers() >= maxPersonnel) {
            throw new IllegalArgumentException("Max personnel exceeded.");
        }
    }

    private void validAmount(SessionAddInfo addInfo) {
        if (!Objects.equals(new SessionAmount(addInfo.getAmount()), this.amount)) {
            throw new IllegalArgumentException("Payment amount does not match.");
        }
    }

    private void validStatus() {
        if (!status.isRecruiting()) {
            throw new IllegalArgumentException("Session is not recruiting.");
        }
    }

    public void add(SessionAddInfo addInfo) {
        validStatus();
        validAmount(addInfo);
        validMaxPersonnel();
        nsUserIds.add(addInfo.getNsUserId());
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
        return Objects.equals(id, session.id) && Objects.equals(period, session.period) && Objects.equals(coverImage, session.coverImage) && feeType == session.feeType && Objects.equals(amount, session.amount) && Objects.equals(maxPersonnel, session.maxPersonnel) && status == session.status && Objects.equals(nsUserIds, session.nsUserIds);
    }
}
