package nextstep.courses.infrastructure;

import static nextstep.courses.factory.EnrollmentStudentConverter.enrollmentStudent;
import static nextstep.util.NullValidator.validateNull;

import java.util.HashSet;
import java.util.Set;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.Session;
import nextstep.users.domain.NsUser;

public class EnrollmentInsertCacheProxy implements Enrollments {

    private final Enrollments enrollments;
    private final Set<EnrollmentStudent> updateCache = new HashSet<>();

    public EnrollmentInsertCacheProxy(Enrollments enrollments) {
        validateNull(enrollments);
        this.enrollments = enrollments;
    }

    @Override
    public void enrollment(Session session, NsUser student) {
        enrollments.enrollment(session, student);
        updateCache.add(enrollmentStudent(session, student));
    }

    @Override
    public void enrollment(EnrollmentStudent student) {
        enrollments.enrollment(student);
        updateCache.add(student);
    }

    Set<EnrollmentStudent> insertEnrollmentStudents() {
        return updateCache;
    }
}
