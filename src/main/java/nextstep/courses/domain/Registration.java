package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

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
}
