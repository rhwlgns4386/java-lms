package nextstep.courses.infrastructure;

import nextstep.courses.domain.EnrollmentsFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcSessionProxyRepository extends JdbcSession2Repository {
    public JdbcSessionProxyRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected EnrollmentsFactory enrollmentFactory() {
        return new ProxyEnrollmentsFactory();
    }
}
