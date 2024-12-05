package nextstep.courses.infrastructure;

import java.util.Optional;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;

public class JdbcSessionRepositoryFacade implements SessionRepository {

    private final JdbcSessionDao jdbcSessionDao;
    private final JdbcImageDao jdbcImageDao;

    public JdbcSessionRepositoryFacade(JdbcSessionDao jdbcSessionDao, JdbcImageDao jdbcImageDao) {
        this.jdbcSessionDao = jdbcSessionDao;
        this.jdbcImageDao = jdbcImageDao;
    }

    @Override
    public Optional<Session> findById(Long id) {
        return jdbcSessionDao.findById(id, jdbcImageDao.findAllBySessionId(id));
    }

    @Override
    public void update(Session session) {
        jdbcSessionDao.update(session);
    }
}
