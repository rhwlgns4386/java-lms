package nextstep.courses.infrastructure.session;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionBuilderTest;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    void update() {
        Session session = SessionBuilderTest.paidSessionBuilder().sessionState(SessionState.OPEN).build();
        sessionRepository.save(session, 1L);
        Session foundSession = sessionRepository.findById(3L);
        foundSession.register(new Payment("test", foundSession.getId(), NsUserTest.JAVAJIGI.getId(), session.getSessionFee()));
        sessionRepository.save(foundSession, 1L);

        Session updatedSession = sessionRepository.findById(3L);
        assertThat(updatedSession.getEnrollment()).isEqualTo(1);
    }
}
