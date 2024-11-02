package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.Objects;

public class Registration {
    private Long id;
    private Long nsUserId;
    private Long amount;

    public Registration(Long id, NsUser user, Payment payment) {
        if (user == null || payment == null) {
            throw new IllegalArgumentException("User or Payment cannot be null");
        }
        if (!user.getId().equals(payment.getNsUserId())) {
            throw new IllegalArgumentException("Payment user_id does not match User");
        }
        this.id = id;
        this.nsUserId = user.getId();
        this.amount = payment.getAmount();
    }

    public Long getId() {
        return id;
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
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
