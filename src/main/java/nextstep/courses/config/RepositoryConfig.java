package nextstep.courses.config;

import nextstep.courses.domain.SessionRepository;
import nextstep.courses.infrastructure.JdbcEnrollmentStudentDao;
import nextstep.courses.infrastructure.JdbcSessionDao;
import nextstep.courses.infrastructure.JdbcSessionRepositoryFacade;
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
}
