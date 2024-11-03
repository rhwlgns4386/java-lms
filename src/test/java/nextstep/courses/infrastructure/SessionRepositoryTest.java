//package nextstep.courses.infrastructure;
//
//import nextstep.courses.domain.image.SessionCoverImage;
//import nextstep.courses.domain.port.SessionCoverImageRepository;
//import nextstep.courses.domain.session.Session;
//import nextstep.courses.domain.session.SessionPriceType;
//import nextstep.courses.domain.session.SessionStatus;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.time.LocalDateTime;
//
//@JdbcTest
//public class SessionRepositoryTest {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private SessionRepository sessionRepository;
//    private SessionCoverImageRepository sessionCoverImageRepository;
//
//    @BeforeEach
//    void setUp() {
//        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
//        sessionCoverImageRepository = new JdbcSessionCoverImageRepository(jdbcTemplate);
//    }
//
//
//    @Test
//    void 세션_저장_테스트() {
//
//        Session session = Session.createSessionOf(1L, 30000L, SessionPriceType.PAID, SessionStatus.PENDING, sessionCoverImage, LocalDateTime.of(2024, 11, 20, 10,30), LocalDateTime.of(2024,12,20,10,30), 20);
//
//    }
//
//}
