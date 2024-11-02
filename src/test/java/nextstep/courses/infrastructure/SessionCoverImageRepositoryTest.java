package nextstep.courses.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.SessionCoverImageRepository;
import nextstep.courses.domain.coverimage.SessionCoverImage;
import nextstep.courses.domain.coverimage.SessionCoverImagePath;
import nextstep.courses.domain.coverimage.SessionCoverImageSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionCoverImageRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionCoverImageRepository sessionCoverImageRepository;

    @BeforeEach
    void setUp() {
        sessionCoverImageRepository = new JdbcSessionCoverImageRepository(jdbcTemplate);
    }


    @Test
    void crud() {
        SessionCoverImagePath path = SessionCoverImagePath.create("/", "example.jpg");
        SessionCoverImageSize size = new SessionCoverImageSize(300, 200);

        SessionCoverImage sessionCoverImage = SessionCoverImage.create(1L, 1 * 1024 * 1024L, path, size);
        int count = sessionCoverImageRepository.save(sessionCoverImage);
        assertThat(count).isEqualTo(1);

        SessionCoverImage savedCoverImage = sessionCoverImageRepository.findById(1L);
        assertThat(savedCoverImage.getSessionCoverImageSize().getWidth()).isEqualTo(300);


    }
}
