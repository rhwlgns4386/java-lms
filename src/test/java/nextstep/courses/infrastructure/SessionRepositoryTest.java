package nextstep.courses.infrastructure;

import static nextstep.courses.domain.TestEnrollmentsFactory.enrollments;
import static nextstep.courses.domain.TestEnrollmentsFactory.limitEnrollments;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import nextstep.courses.domain.Charge;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

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
    void findSession() {
        Session session = sessionRepository.findById(1L).orElseThrow();
        Enrollments enrollments = session.getEnrollments();

        assertThat(session.getId()).isEqualTo(1L);
        assertThat(enrollments).isEqualTo(
                enrollments(SessionStatus.ENROLLING, session, Set.of(NsUserTest.SANJIGI, NsUserTest.JAVAJIGI)));
    }

    @Test
    void update() {
        Session session = sessionRepository.findById(2L).orElseThrow();
        session.enrollment(new Charge(100), NsUserTest.SANJIGI);
        sessionRepository.update(session);

        Session resultSession = sessionRepository.findById(2L).orElseThrow();
        assertThat(resultSession.getId()).isEqualTo(2L);
        assertThat(resultSession.getEnrollments()).isEqualTo(limitEnrollments(6, SessionStatus.ENROLLING, resultSession,
                Set.of(NsUserTest.SANJIGI, NsUserTest.JAVAJIGI)));
    }
}
