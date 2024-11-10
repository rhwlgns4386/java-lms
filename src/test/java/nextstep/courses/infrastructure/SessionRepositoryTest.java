package nextstep.courses.infrastructure;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.ProgressCode;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionTest;
import nextstep.courses.domain.SessionType;
import nextstep.courses.domain.StateCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("유료 강의 등록 / 찾기")
    void sessionRegisterCRUD_paid() {
        int sessionCnt = sessionRepository.saveRegisterSession(SessionTest.SESSION_NO_RECRUITING_PROGRESS);
        assertThat(SessionTest.SESSION_NO_RECRUITING_PROGRESS.getSessionImages()).hasSize(sessionCnt);

        String sql = "SELECT session_id FROM session ORDER BY session_id DESC LIMIT 1";
        long sessionId = jdbcTemplate.queryForObject(sql, new HashMap<>(), Long.class);

        Session session = sessionRepository.findSessionInfoById(sessionId);

        assertThat(session.getSessionTypeCode()).isEqualTo(SessionType.PAID.getTypeCode());
        assertThat(session.getStateCode()).isEqualTo(StateCode.NO_RECRUITING.getStatusCode());
        assertThat(session.getProgressCode()).isEqualTo(ProgressCode.PROGRESS.getProgressCode());
        assertThat(session).isInstanceOf(PaidSession.class);

        assertThat(session.getSessionImages()).hasSize(2);
    }

    @Test
    @DisplayName("무료 강의 등록 / 찾기")
    void sessionRegisterCRUD_free() {
        int sessionCnt = sessionRepository.saveRegisterSession(SessionTest.SESSION_NO_RECRUITING_END);
        assertThat(SessionTest.SESSION_NO_RECRUITING_PROGRESS.getSessionImages()).hasSize(sessionCnt);

        String sql = "SELECT session_id FROM session ORDER BY session_id DESC LIMIT 1";
        long sessionId = jdbcTemplate.queryForObject(sql, new HashMap<>(), Long.class);

        Session session = sessionRepository.findSessionInfoById(sessionId);

        assertThat(session.getSessionTypeCode()).isEqualTo(SessionType.FREE.getTypeCode());
        assertThat(session.getStateCode()).isEqualTo(StateCode.NO_RECRUITING.getStatusCode());
        assertThat(session.getProgressCode()).isEqualTo(ProgressCode.END.getProgressCode());
        assertThat(session).isInstanceOf(FreeSession.class);

        assertThat(session.getSessionImages()).hasSize(2);
    }



}
