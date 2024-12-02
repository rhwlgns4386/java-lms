package nextstep.courses.domain;

import java.util.Set;
import nextstep.users.domain.NsUser;

public interface Enrollments {
    void enrollment(Session session, NsUser student);

    void enrollment(EnrollmentStudent student);

    SessionStatus sessionStatus();

    Set<EnrollmentStudent> enrolledStudents();
}
