package nextstep.courses.domain.session.enrollment;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public interface Enrollment {
    void enroll(NsUser student, Payment payment);

    void approve(List<Long> userIdList);
}
