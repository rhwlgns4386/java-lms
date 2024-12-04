package nextstep.courses.infrastructure;

import nextstep.courses.domain.EnrollmentsFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcSessionProxyDao extends JdbcSession2Dao {
    public JdbcSessionProxyDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected EnrollmentsFactory enrollmentFactory() {
        return new ProxyEnrollmentsFactory();
    }
}
