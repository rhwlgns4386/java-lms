package nextstep.session.domain;

import java.time.LocalDateTime;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Enrollment {
    private final Long id;
    private final Session session;
    private final NsUser nsUser;
    private final LocalDateTime enrollmentDate;
    private final Payment payment;

    private Enrollment(Long id, Session session, NsUser nsUser, Payment payment) {
        this.id = id;
        this.session = session;
        this.nsUser = nsUser;
        this.enrollmentDate = LocalDateTime.now();
        this.payment = payment;
    }

    public static Enrollment free(Long id, Session session, NsUser nsUser) {
        return new Enrollment(id, session, nsUser, null);
    }

    public static Enrollment paid(Long id, Session session, NsUser nsUser, Payment payment) {
        return new Enrollment(id, session, nsUser, payment);
    }

    public Long getPaymentAmount() {
        return payment.getAmount();
    }
}
