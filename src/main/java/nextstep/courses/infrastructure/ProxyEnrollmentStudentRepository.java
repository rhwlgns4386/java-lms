package nextstep.courses.infrastructure;

import java.util.Set;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.Enrollments;

public class ProxyEnrollmentStudentRepository extends JdbcEnrollmentRepositoryAdapter {
    public ProxyEnrollmentStudentRepository(JdbcEnrollmentStudentDao jdbcEnrollmentStudentDao) {
        super(jdbcEnrollmentStudentDao);
    }

    @Override
    protected Set<EnrollmentStudent> updatedItems(Enrollments enrollments) {
        if (enrollments instanceof EnrollmentsInsertCacheProxy) {
            EnrollmentsInsertCacheProxy enrollmentsInsertCacheProxy = (EnrollmentsInsertCacheProxy) enrollments;
            return enrollmentsInsertCacheProxy.newEnrollmentStudent();
        }
        return super.updatedItems(enrollments);
    }
}
