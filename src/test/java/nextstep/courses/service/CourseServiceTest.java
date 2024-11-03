package nextstep.courses.service;

import nextstep.courses.entity.SessionEntity;
import nextstep.courses.entity.SessionEntityTest;
import nextstep.courses.infrastructure.course.CourseRepository;
import nextstep.courses.infrastructure.course.JdbcCourseRepository;
import nextstep.courses.infrastructure.cover.JdbcCoverImageRepository;
import nextstep.courses.infrastructure.session.JdbcSessionRepository;
import nextstep.courses.infrastructure.session.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcOperations;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class CourseServiceTest {

    @Autowired
    private JdbcOperations jdbcOperations;

    private CourseService courseService;
    private SessionRepository sessionRepository;

    @BeforeEach
    void init() {
        sessionRepository = new JdbcSessionRepository(jdbcOperations, new JdbcCoverImageRepository(jdbcOperations));
        CourseRepository courseRepository = new JdbcCourseRepository(jdbcOperations, sessionRepository);
        courseService = new CourseService(courseRepository, sessionRepository);
    }

    @Test
    void test_register_with_db() {
        courseService.registerSessionEntity(1L,
                new Payment("테스트", 2L, NsUserTest.JAVAJIGI.getId(), 10000L));

        SessionEntity session = sessionRepository.findById(2L);

        assertThat(session.getEnrollment()).isEqualTo(1);
    }
}
