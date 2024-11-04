package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.Objects;

public class Registration {
    private Long sessionId;
    private Long nsUserId;
    private Long amount;

    public Registration(Long sessionId, NsUser user, Payment payment) {
        if (user == null || payment == null) {
            throw new IllegalArgumentException("User or Payment cannot be null");
        }
        if (!user.getId().equals(payment.getNsUserId())) {
            throw new IllegalArgumentException("Payment user_id does not match User");
        }
        if (!user.isStudent()) {
            throw new IllegalArgumentException("user is not a student");
        }
        this.sessionId = sessionId;
        this.nsUserId = user.getId();
        this.amount = payment.getAmount();
    }

    public static Registration of(Long sessionId, NsUser user, Payment payment) {
        return new Registration(sessionId, user, payment);
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public Long getAmount() {
        return amount;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Registration)) {
            return false;
        }

        Registration that = (Registration) o;
        return Objects.equals(getSessionId(), that.getSessionId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSessionId());
    }
}
