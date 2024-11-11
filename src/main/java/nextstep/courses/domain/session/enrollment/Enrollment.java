package nextstep.courses.domain.session.enrollment;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public interface Enrollment {
    void enroll(NsUser student, Payment payment);

}
