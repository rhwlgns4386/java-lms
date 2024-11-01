package nextstep.sessions.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.courses.infrastructure.JdbcCourseRepository;
import nextstep.sessions.domain.ApplicationDetail;
import nextstep.sessions.domain.ApplicationDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class ApplicationDetailRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationDetailRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ApplicationDetailRepository applicationDetailRepository;

    @BeforeEach
    void setUp() {
        applicationDetailRepository = new JdbcApplicationDetailRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        ApplicationDetail applicationDetail = new ApplicationDetail(1L, 1L,  LocalDateTime.now(), null);
        int count = applicationDetailRepository.save(applicationDetail);
        assertThat(count).isEqualTo(1);
        Optional<ApplicationDetail> savedapplicationDetail = applicationDetailRepository.findByUserAndSession(1L, 1L);
        assertThat(savedapplicationDetail.isPresent()).isTrue();
        assertThat(applicationDetail.getSessionId()).isEqualTo(savedapplicationDetail.get().getSessionId());
        List<ApplicationDetail> saveApplicationDetailList = applicationDetailRepository.findBySession(1L);
        assertThat(saveApplicationDetailList.size()).isEqualTo(1);
        LOGGER.debug("ApplicationDetail: {}", savedapplicationDetail);
    }
}
