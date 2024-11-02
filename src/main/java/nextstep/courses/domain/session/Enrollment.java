package nextstep.courses.domain.session;

import java.time.LocalDateTime;

import nextstep.users.domain.NsUser;

public class Enrollment {
    private final long id;
    private final Session session;
    private final NsUser nsUser;
    private final LocalDateTime enrollmentDate;
    private final Long paymentAmount;

    public Enrollment(long id, Session session, NsUser nsUser, LocalDateTime enrollmentDate, Long paymentAmount) {
        this.id = id;
        this.session = session;
        this.nsUser = nsUser;
        this.enrollmentDate = enrollmentDate;
        this.paymentAmount = paymentAmount;
    }

    public Long getPaymentAmount() {
        return paymentAmount;
    }
}
