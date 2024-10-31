package nextstep.sessions.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.courses.infrastructure.JdbcCourseRepository;
import nextstep.sessions.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionImageRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionImageRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionImageRepository sessionImageRepository;

    @BeforeEach
    void setUp() {
        sessionImageRepository = new JdbcSessionImageRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        SessionImage image = new SessionImage("image/test.jpg", new ImageSize(Long.valueOf(1024 * 1024)), SessionImageTypeEnum.JPG, new SessionImagePixel(new ImageWidth(300), new ImageHeight(200)));
        image.toSession(new Session(1L, null, new SessionPeriod("20250101", "20250301"), new SessionType(), new SessionStatus(SessionStatusEnum.PREPARING), LocalDateTime.now(), null));
        int count = sessionImageRepository.save(image);
        assertThat(count).isEqualTo(1);
        Optional<SessionImage> savedimage = sessionImageRepository.findBySession(1L);
        assertThat(savedimage.isPresent()).isTrue();
        assertThat(savedimage.get().getImageType()).isSameAs(SessionImageTypeEnum.JPG);
        LOGGER.debug("SessionImage: {}", savedimage);
    }
}
