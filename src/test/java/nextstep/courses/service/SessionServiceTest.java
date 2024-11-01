package nextstep.courses.service;

import nextstep.courses.entity.SessionEntity;
import nextstep.courses.infrastructure.course.CourseRepository;
import nextstep.courses.infrastructure.course.JdbcCourseRepository;
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
public class SessionServiceTest {

    @Autowired
    private JdbcOperations jdbcOperations;

    private SessionService sessionService;
    private SessionRepository sessionRepository;

    @BeforeEach
    void init() {
        sessionRepository = new JdbcSessionRepository(jdbcOperations);
        sessionService = new SessionService(sessionRepository);
    }

    @Test
    void test_register_with_db() {
        sessionService.register(1L,
                new Payment("테스트", 1L, NsUserTest.JAVAJIGI.getId(), 0L));

        SessionEntity session = sessionRepository.findById(1L);

        assertThat(session.getEnrollment()).isEqualTo(1);
    }
}
