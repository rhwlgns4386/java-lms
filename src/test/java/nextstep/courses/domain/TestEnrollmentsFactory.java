package nextstep.courses.domain;

import static nextstep.courses.factory.EnrollmentStudentConverter.enrollmentStudent;

import java.util.Set;
import java.util.stream.Collectors;
import nextstep.users.domain.NsUser;

public class TestEnrollmentsFactory {
    private TestEnrollmentsFactory() {
    }

    public static Enrollments enrollments(SessionStatus sessionStatus, Session session) {
        return enrollments(sessionStatus, session, Set.of());
    }

    public static Enrollments enrollments(SessionStatus sessionStatus, Session session, Set<NsUser> enrolledStudents) {
        return new Enrollments(sessionStatus, toStudentSet(session, enrolledStudents));
    }

    public static Enrollments limitEnrollments(int capacity, SessionStatus sessionStatus, Session session,
                                               Set<NsUser> users) {
        return new LimitedEnrollments(capacity, sessionStatus, toStudentSet(session, users));
    }

    private static Set<EnrollmentStudent> toStudentSet(Session session, Set<NsUser> enrolledStudents) {
        return enrolledStudents.stream().map((user) -> enrollmentStudent(session, user)).collect(Collectors.toSet());
    }

}
