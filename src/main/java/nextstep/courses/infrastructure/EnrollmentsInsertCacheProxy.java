package nextstep.courses.infrastructure;

import static nextstep.courses.factory.EnrollmentStudentConverter.enrollmentStudent;

import java.util.HashSet;
import java.util.Set;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionStatus;
import nextstep.users.domain.NsUser;

public class EnrollmentsInsertCacheProxy implements Enrollments {

    private final Enrollments enrollments;
    private final Set<EnrollmentStudent> cache = new HashSet<>();

    public EnrollmentsInsertCacheProxy(Enrollments enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public void enrollment(Session session, NsUser student) {
        EnrollmentStudent enrollmentStudent = enrollmentStudent(session, student);
        enrollment(enrollmentStudent);
    }

    @Override
    public void enrollment(EnrollmentStudent student) {
        enrollments.enrollment(student);
        cache.add(student);
    }

    @Override
    public SessionStatus sessionStatus() {
        return enrollments.sessionStatus();
    }

    @Override
    public Set<EnrollmentStudent> enrolledStudents() {
        return enrollments.enrolledStudents();
    }

    public Set<EnrollmentStudent> newEnrollmentStudent() {
        return cache;
    }
}
