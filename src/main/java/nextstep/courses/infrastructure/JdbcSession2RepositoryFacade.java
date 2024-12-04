package nextstep.courses.infrastructure;

import java.util.Optional;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;

public class JdbcSession2RepositoryFacade implements SessionRepository {

    private final JdbcSession2Dao jdbcSession2Dao;
    private final JdbcImageDao jdbcImageDao;

    public JdbcSession2RepositoryFacade(JdbcSession2Dao jdbcSession2Dao, JdbcImageDao jdbcImageDao) {
        this.jdbcSession2Dao = jdbcSession2Dao;
        this.jdbcImageDao = jdbcImageDao;
    }

    @Override
    public Optional<Session> findById(Long id) {
        return jdbcSession2Dao.findById(id, jdbcImageDao.findAllBySessionId(id));
    }

    @Override
    public void update(Session session) {
        jdbcSession2Dao.update(session);
    }
}
