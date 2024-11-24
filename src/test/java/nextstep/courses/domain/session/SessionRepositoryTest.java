package nextstep.courses.domain.session;

import nextstep.courses.domain.session.enrollment.EnrollmentStatus;
import nextstep.courses.domain.session.enrollment.PayType;
import nextstep.courses.domain.session.enrollment.Status;
import nextstep.courses.domain.session.entity.SessionEntity;
import nextstep.courses.domain.session.sessioncoverimage.SessionCoverImageRepository;
import nextstep.courses.infrastructure.JdbcSessionCoverImageRepository;
import nextstep.courses.infrastructure.JdbcSessionRepository;
import nextstep.users.domain.UserRepository;
import nextstep.users.infrastructure.JdbcUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

@JdbcTest
class SessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    private SessionCoverImageRepository sessionCoverImageRepository;

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new JdbcUserRepository(jdbcTemplate);
        sessionCoverImageRepository = new JdbcSessionCoverImageRepository(jdbcTemplate);
        sessionRepository = new JdbcSessionRepository(jdbcTemplate, sessionCoverImageRepository, userRepository);
    }

    @Test
    void crud() {
        SessionEntity sessionEntity = new SessionEntity("강의3",
                                                        1L,
                                                        Status.CLOSE.name(),
                                                        EnrollmentStatus.IMPOSSIBLE.name(),
                                                        800_000L,
                                                        PayType.PAY.name(),
                                                        100,
                                                        1L,
                                                        LocalDateTime.now(),
                                                        LocalDateTime.now().plusDays(30));

        int count = sessionRepository.save(sessionEntity);
        Assertions.assertThat(count).isEqualTo(1);

        SessionEntity savedSessionEntity = sessionRepository.findById(3);
        Assertions.assertThat(savedSessionEntity.getId()).isEqualTo(3);
        Assertions.assertThat(savedSessionEntity.getTitle()).isEqualTo("강의3");
    }

    @Test
    void findByIdForSession() {
        Session session = sessionRepository.findByIdForSession(1);
        Assertions.assertThat(session).isNotNull();
    }
}