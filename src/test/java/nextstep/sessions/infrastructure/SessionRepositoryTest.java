package nextstep.sessions.infrastructure;

import nextstep.courses.domain.CourseTest;
import nextstep.payments.domain.PaymentTest;
import nextstep.sessions.domain.EnrolledUserInfos;
import nextstep.sessions.domain.PeriodTest;
import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.SessionImageRepository;
import nextstep.sessions.domain.SessionImageTest;
import nextstep.sessions.domain.SessionRegistrationInfoRepository;
import nextstep.sessions.domain.SessionRepository;
import nextstep.sessions.domain.SessionStatus;
import nextstep.sessions.domain.SessionTypeTest;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    private SessionImageRepository sessionImageRepository;

    private SessionRegistrationInfoRepository sessionRegistrationInfoRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
        sessionImageRepository = new JdbcSessionImageRepository(jdbcTemplate);
        sessionRegistrationInfoRepository = new JdbcSessionRegistrationInfoRepository(jdbcTemplate);
    }

    @Test
    void create_read_test() {
        sessionImageRepository.save(SessionImageTest.IMAGE);
        Session session = new Session(1L, SessionImageTest.IMAGE, PeriodTest.PERIOD, SessionTypeTest.PAID_TYPE
                , SessionStatus.PREPARING, CourseTest.COURSE, LocalDateTime.now(), LocalDateTime.now());
        int count = sessionRepository.save(session);
        assertThat(count).isEqualTo(1);
        Session savedSession = sessionRepository.findById(1L).get();
        assertThat(session.getId()).isEqualTo(savedSession.getId());
        LOGGER.debug("Session: {}", savedSession);
    }

    @Test
    void update_test() {
        sessionImageRepository.save(SessionImageTest.IMAGE);
        Session session = new Session(1L, SessionImageTest.IMAGE, PeriodTest.PERIOD, SessionTypeTest.PAID_TYPE
                , SessionStatus.PREPARING, CourseTest.COURSE, LocalDateTime.now(), LocalDateTime.now());
        sessionRepository.save(session);
        session.updateToRecruiting();
        sessionRepository.update(session);
        Session updatedSession = sessionRepository.findById(1L).get();
        assertThat(session.getStatus()).isEqualTo(updatedSession.getStatus());
    }

    @Test
    void lazy_loading_test() {
        // data 생성
        sessionImageRepository.save(SessionImageTest.IMAGE);
        Session session = new Session(1L, SessionImageTest.IMAGE, PeriodTest.PERIOD, SessionTypeTest.FREE_TYPE
                , SessionStatus.PREPARING, CourseTest.COURSE, LocalDateTime.now(), LocalDateTime.now());
        session.updateToRecruiting();
        session.enroll(NsUserTest.JAVAJIGI, PaymentTest.PAYMENT1);
        sessionRepository.save(session);
        if (session.getEnrolledUserInfosSize() > 0) {
            session.getEnrolledUserInfos().getInfos().forEach(info -> {
                sessionRegistrationInfoRepository.save(info);
            });
        }

        // data 조회
        Session savedSession = sessionRepository.findById(1L).get();
        EnrolledUserInfos infos = new EnrolledUserInfos(sessionRegistrationInfoRepository.findBySessionId(savedSession.getId()));
        savedSession.setEnrolledUserInfos(infos);

        assertThat(session.getEnrolledUserInfosSize()).isEqualTo(savedSession.getEnrolledUserInfosSize());
        LOGGER.debug("Session: {}", savedSession);
    }
}
