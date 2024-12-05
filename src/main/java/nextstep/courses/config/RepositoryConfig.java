package nextstep.courses.config;

import nextstep.courses.domain.EnrollmentStudentRepository;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.infrastructure.JdbcEnrollmentRepository;
import nextstep.courses.infrastructure.JdbcEnrollmentStudentDao;
import nextstep.courses.infrastructure.JdbcImageDao;
import nextstep.courses.infrastructure.JdbcSessionDao;
import nextstep.courses.infrastructure.JdbcSessionRepositoryFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class RepositoryConfig {

    @Bean
    public SessionRepository session2Repository(JdbcTemplate jdbcTemplate) {
        return new JdbcSessionRepositoryFacade(new JdbcSessionDao(jdbcTemplate), new JdbcImageDao(jdbcTemplate));
    }

    @Bean
    public EnrollmentStudentRepository enrollmentStudentRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcEnrollmentRepository(new JdbcEnrollmentStudentDao(jdbcTemplate));
    }

}
