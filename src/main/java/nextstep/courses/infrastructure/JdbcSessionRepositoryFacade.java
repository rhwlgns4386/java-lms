package nextstep.courses.infrastructure;

import java.util.Optional;
import java.util.Set;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.util.Sets;

public class JdbcSessionRepositoryFacade implements SessionRepository {
    private final JdbcSessionDao jdbcSessionDao;
    private final JdbcEnrollmentStudentDao jdbcEnrollmentStudentDao;

    public JdbcSessionRepositoryFacade(JdbcSessionDao jdbcSessionDao,
                                       JdbcEnrollmentStudentDao jdbcEnrollmentStudentDao) {
        this.jdbcSessionDao = jdbcSessionDao;
        this.jdbcEnrollmentStudentDao = jdbcEnrollmentStudentDao;
    }

    @Override
    public Optional<Session> findById(Long id) {
        Set<EnrollmentStudent> enrollmentStudents = jdbcEnrollmentStudentDao.findAllEnrollmentStudentBySessionId(id);
        return jdbcSessionDao.findById(id, enrollmentStudents);
    }

    @Override
    public void update(Session session) {
        jdbcEnrollmentStudentDao.insertItem(newEnrolledStudents(session));
        jdbcSessionDao.update(session);
    }

    private Set<EnrollmentStudent> newEnrolledStudents(Session session) {
        Set<EnrollmentStudent> before = jdbcEnrollmentStudentDao.findAllEnrollmentStudentBySessionId(
                session.id());
        Set<EnrollmentStudent> newItem = session.enrollmentStudents();
        return Sets.difference(newItem, before);
    }
}
