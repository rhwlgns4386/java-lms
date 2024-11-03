package nextstep.courses.service;

import nextstep.courses.entity.SessionEntity;
import nextstep.courses.entity.SessionEntityTest;
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
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

@JdbcTest
public class SessionServiceTest {

    @Autowired
    private JdbcOperations jdbcOperations;

    private SessionService sessionService;
    private SessionRepository sessionRepository;

    @BeforeEach
    void init() {
        sessionRepository = new JdbcSessionRepository(jdbcOperations, new JdbcCoverImageRepository(jdbcOperations));
        sessionService = new SessionService(sessionRepository);
    }

    @Test
    void throw_exception_if_register_not_recruit_session() {
        assertThatIllegalStateException().isThrownBy(() -> sessionService.register(1L,
                new Payment("테스트", 1L, NsUserTest.JAVAJIGI.getId(), 0L)));
    }

    @Test
    void throw_exception_if_register_not_invalid_session_id() {
        assertThatIllegalStateException().isThrownBy(() -> sessionService.register(2L,
                new Payment("테스트", 1L, NsUserTest.JAVAJIGI.getId(), 10000L)));
    }

    @Test
    void throw_exception_if_register_invalid_session_fee() {
        assertThatIllegalStateException().isThrownBy(() -> sessionService.register(2L,
                new Payment("테스트", 2L, NsUserTest.JAVAJIGI.getId(), 0L)));
    }

    @Test
    void test_register_with_db() {
        sessionService.register(2L,
                new Payment("테스트", 2L, NsUserTest.JAVAJIGI.getId(), 10000L));

        SessionEntity session = sessionRepository.findById(2L);

        assertThat(session.getEnrollment()).isEqualTo(1);
    }
}
