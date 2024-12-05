package nextstep.courses.infrastructure;

import java.util.Set;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.EnrollmentStudentRepository;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.RequestStatus;

public class JdbcEnrollmentRepository implements EnrollmentStudentRepository {

    private final JdbcEnrollmentStudentDao jdbcEnrollmentStudentDao;

    public JdbcEnrollmentRepository(JdbcEnrollmentStudentDao jdbcEnrollmentStudentDao) {
        this.jdbcEnrollmentStudentDao = jdbcEnrollmentStudentDao;
    }

    @Override
    public Set<EnrollmentStudent> findBySessionId(long sessionId) {
        return jdbcEnrollmentStudentDao.findAllEnrollmentStudentBySessionId(sessionId);
    }

    @Override
    public void update(Enrollments enrollments) {
        jdbcEnrollmentStudentDao.deleteAllBySessionId(enrollments.enrolledStudents());
        jdbcEnrollmentStudentDao.insertItem(enrollments.enrolledStudents());
    }

    @Override
    public Set<EnrollmentStudent> findByPendingStudentSessionId(long sessionId) {
        return jdbcEnrollmentStudentDao.findAllEnrollmentStudentBySessionIdAndRequestStatus(sessionId,
                RequestStatus.PENDING);
    }

}
