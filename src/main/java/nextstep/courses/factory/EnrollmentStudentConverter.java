package nextstep.courses.factory;

import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.Session;
import nextstep.users.domain.NsUser;

public class EnrollmentStudentConverter {
    public static EnrollmentStudent enrollmentStudent(Session session, NsUser user) {
        return new EnrollmentStudent(session, user);
    }
}
