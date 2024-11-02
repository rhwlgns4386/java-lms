package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionImageSize;
import nextstep.courses.domain.SessionImageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcOperations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@JdbcTest
public class SessionImageRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionImageRepositoryTest.class);

    @Autowired
    private JdbcOperations jdbcTemplate;

    private SessionImageRepository sessionImageRepository;

    @BeforeEach
    void setUp() {
        sessionImageRepository = new JdbcSessionImageRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        SessionImage sessionImage = new SessionImage(512, SessionImageType.GIF, new SessionImageSize(400, 300));
        int count = sessionImageRepository.save(sessionImage);
        SessionImage savedSessionImage = sessionImageRepository.findById(1L);
        assertAll(
                () -> assertThat(count).isEqualTo(1),
                () -> assertThat(sessionImage.getVolume()).isEqualTo(savedSessionImage.getVolume()),
                () -> assertThat(sessionImage.getType()).isEqualTo(savedSessionImage.getType()),
                () -> assertThat(sessionImage.getSessionImageSize()).isEqualTo(savedSessionImage.getSessionImageSize())
        );
        LOGGER.debug("SessionImage: {}", savedSessionImage);
    }
}
