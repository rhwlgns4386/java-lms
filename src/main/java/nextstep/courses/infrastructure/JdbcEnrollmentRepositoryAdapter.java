package nextstep.courses.infrastructure;

import java.util.Set;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.EnrollmentStudentRepository;
import nextstep.courses.domain.Session;
import nextstep.util.Sets;

public class JdbcEnrollmentRepositoryAdapter implements EnrollmentStudentRepository {

    private final JdbcEnrollmentStudentDao jdbcEnrollmentStudentDao;

    public JdbcEnrollmentRepositoryAdapter(JdbcEnrollmentStudentDao jdbcEnrollmentStudentDao) {
        this.jdbcEnrollmentStudentDao = jdbcEnrollmentStudentDao;
    }

    @Override
    public Set<EnrollmentStudent> findBySessionId(long sessionId) {
        return jdbcEnrollmentStudentDao.findAllEnrollmentStudentBySessionId(sessionId);
    }

    @Override
    public void update(Session session) {
        jdbcEnrollmentStudentDao.insertItem(newEnrolledStudents(session));
    }

    private Set<EnrollmentStudent> newEnrolledStudents(Session session) {
        Set<EnrollmentStudent> before = jdbcEnrollmentStudentDao.findAllEnrollmentStudentBySessionId(
                session.id());
        Set<EnrollmentStudent> newItem = session.enrollmentStudents();
        return Sets.difference(newItem, before);
    }
}
