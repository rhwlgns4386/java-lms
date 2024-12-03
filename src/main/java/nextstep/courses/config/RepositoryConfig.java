package nextstep.courses.config;

import nextstep.courses.domain.EnrollmentStudentRepository;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.infrastructure.JdbcEnrollmentRepositoryAdapter;
import nextstep.courses.infrastructure.JdbcEnrollmentStudentDao;
import nextstep.courses.infrastructure.JdbcSession2Repository;
import nextstep.courses.infrastructure.JdbcSessionDao;
import nextstep.courses.infrastructure.JdbcSessionProxyRepository;
import nextstep.courses.infrastructure.JdbcSessionRepositoryFacade;
import nextstep.courses.infrastructure.ProxyEnrollmentStudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class RepositoryConfig {

    @Bean
    public SessionRepository sessionRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcSessionRepositoryFacade(new JdbcSessionDao(jdbcTemplate),
                new JdbcEnrollmentStudentDao(jdbcTemplate));
    }

    @Bean
    public SessionRepository session2Repository(JdbcTemplate jdbcTemplate) {
        return new JdbcSession2Repository(jdbcTemplate);
    }

    @Bean
    public SessionRepository proxySessionRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcSessionProxyRepository(jdbcTemplate);
    }

    @Bean
    public EnrollmentStudentRepository enrollmentStudentRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcEnrollmentRepositoryAdapter(new JdbcEnrollmentStudentDao(jdbcTemplate));
    }

    @Bean
    public EnrollmentStudentRepository proxyEnrollmentStudentRepository(JdbcTemplate jdbcTemplate) {
        return new ProxyEnrollmentStudentRepository(new JdbcEnrollmentStudentDao(jdbcTemplate));
    }
}
