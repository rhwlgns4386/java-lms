package nextstep.session.domain;

import java.time.LocalDateTime;

import nextstep.courses.utils.UUIDGenerator;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Enrollment {
    private final String id;
    private final Session session;
    private final NsUser nsUser;
    private final LocalDateTime enrollmentDate;
    private final Payment payment;

    public Enrollment(Session session, NsUser nsUser, Payment payment) {
        this.id = UUIDGenerator.getUUID();
        this.session = session;
        this.nsUser = nsUser;
        this.enrollmentDate = LocalDateTime.now();
        this.payment = payment;
    }

    public Long getPaymentAmount() {
        return payment.getAmount();
    }
}
