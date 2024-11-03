package nextstep.courses.infrastructure;

import nextstep.courses.domain.image.SessionCoverImage;
import nextstep.courses.domain.port.SessionCoverImageRepository;
import nextstep.courses.domain.port.SessionRepository;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionPriceType;
import nextstep.courses.domain.session.SessionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;
    private SessionCoverImageRepository sessionCoverImageRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
        sessionCoverImageRepository = new JdbcSessionCoverImageRepository(jdbcTemplate);
    }


    @Test
    void 세션_저장_테스트() {
        Session session = new Session.SessionBuilder()
                .price(1000L)
                .sessionPriceType(SessionPriceType.FREE)
                .sessionStatus(SessionStatus.COMPLETED)
                .startDateTime(LocalDateTime.of(2024, 10, 30, 10, 30))
                .endDateTime(LocalDateTime.of(2024, 11, 20, 10, 30))
                .availableEnrollCount(30).price(1000L)
                .sessionPriceType(SessionPriceType.FREE)
                .sessionStatus(SessionStatus.COMPLETED)
                .startDateTime(LocalDateTime.of(2024, 10, 30, 10, 30))
                .endDateTime(LocalDateTime.of(2024, 11, 20, 10, 30))
                .availableEnrollCount(30)
                .build();

        Long key = sessionRepository.save(session);
        SessionCoverImage sessionCoverImage = new SessionCoverImage.SessionCoverImageBuilder().id(1L).sessionId(key).fileName("leo.png").filePath("/home/lms/image/cover/leo.png").volume(150).width(300).height(200).build();

        sessionCoverImageRepository.save(sessionCoverImage);

        Session savedSession = sessionRepository.findById(1L);
        assertThat(key).isEqualTo(1L);
        assertThat(savedSession.getSessionId()).isEqualTo(1L);
    }

}
