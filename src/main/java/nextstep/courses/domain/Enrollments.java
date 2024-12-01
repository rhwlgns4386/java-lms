package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public interface Enrollments {
    void enrollment(Session session, NsUser student);

    void enrollment(EnrollmentStudent student);
}
