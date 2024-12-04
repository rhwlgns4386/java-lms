package nextstep.courses.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.util.Sets;

public class JdbcSessionRepositoryFacade implements SessionRepository {
    private final JdbcSessionDao jdbcSessionDao;
    private final JdbcEnrollmentStudentDao jdbcEnrollmentStudentDao;
    private final JdbcImageDao imageDao;

    public JdbcSessionRepositoryFacade(JdbcSessionDao jdbcSessionDao,
                                       JdbcEnrollmentStudentDao jdbcEnrollmentStudentDao, JdbcImageDao imageDao) {
        this.jdbcSessionDao = jdbcSessionDao;
        this.jdbcEnrollmentStudentDao = jdbcEnrollmentStudentDao;
        this.imageDao = imageDao;
    }

    @Override
    public Optional<Session> findById(Long id) {
        Set<EnrollmentStudent> enrollmentStudents = jdbcEnrollmentStudentDao.findAllEnrollmentStudentBySessionId(id);
        List<CoverImage> coverImages = this.imageDao.findAllBySessionId(id);
        return jdbcSessionDao.findById(id, enrollmentStudents, coverImages);
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
