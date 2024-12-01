package nextstep.courses.domain;

import static nextstep.courses.factory.EnrollmentStudentConverter.enrollmentStudent;

import java.util.Set;
import java.util.stream.Collectors;
import nextstep.users.domain.NsUser;

public class TestEnrollmentsFactory {
    private TestEnrollmentsFactory() {
    }

    static Enrollments enrollments(SessionStatus sessionStatus, Session session) {
        return enrollments(sessionStatus, session, Set.of());
    }

    static Enrollments enrollments(SessionStatus sessionStatus, Session session, Set<NsUser> enrolledStudents) {
        return new DefaultEnrollments(sessionStatus, toStudentSet(session, enrolledStudents));
    }

    static Enrollments limitEnrollments(int capacity, SessionStatus sessionStatus, Session session,
                                        Set<NsUser> users) {
        return new LimitedEnrollments(capacity, sessionStatus, toStudentSet(session, users));
    }

    private static Set<EnrollmentStudent> toStudentSet(Session session, Set<NsUser> enrolledStudents) {
        return enrolledStudents.stream().map((user) -> enrollmentStudent(session, user)).collect(Collectors.toSet());
    }

}
