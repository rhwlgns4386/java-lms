package nextstep.courses.infrastructure;

import nextstep.courses.collection.Students;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionTest;
import nextstep.courses.domain.SessionType;
import nextstep.courses.domain.StateCode;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("강의 등록 / 찾기")
    void sessionRegisterCRUD() {
        int count = sessionRepository.saveRegisterSession(SessionTest.SESSION_REDAY);
        assertThat(count).isEqualTo(1);

        Session session = sessionRepository.findSessionInfoById(1L);
        assertThat(session.getSessionTypeCode()).isEqualTo(SessionType.PAID.getTypeCode());
        assertThat(session.getStateCode()).isEqualTo(StateCode.READY.getStatusCode());
        assertThat(session).isInstanceOf(PaidSession.class);

        LOGGER.debug("=====================");
        LOGGER.debug("Session: {}", session);
    }


    @Test
    @DisplayName("주문 조회 / 주문 등록")
    void sessionOrderCRUD() {
        Session session = sessionRepository.findSessionInfoById(1L);
        int count = sessionRepository.saveOrderSession(NsUserTest.JAVAJIGI, session);

        assertThat(count).isEqualTo(1);

        sessionRepository.saveOrderSession(NsUserTest.SANJIGI, session);
        sessionRepository.saveOrderSession(new NsUser(3L), session);

        Students students = sessionRepository.findOrderInfoBySessionId(1L);

        assertThat(students.getStudents()).hasSize(3);
        assertThat(students.getStudents()).contains(new NsUser(1L), new NsUser(2L), new NsUser(3L));

        LOGGER.debug("=====================");
        LOGGER.debug("Session: {}", session);
        LOGGER.debug("=====================");
        LOGGER.debug("students: {}", students);
    }

}
