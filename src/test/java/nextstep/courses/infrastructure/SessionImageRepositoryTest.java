package nextstep.courses.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import nextstep.courses.domain.Images;
import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionImageRepository;
import nextstep.courses.domain.SessionTestFixture;

@JdbcTest
class SessionImageRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionImageRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionImageRepository sessionImageRepository;

    @BeforeEach
    void setUp() {
        sessionImageRepository = new JdbcSessionImageRepository(jdbcTemplate);
    }

    @DisplayName("Images를 가지고 생성한 SessionImage객체를 저장한다")
    @Test
    void save() {
        Images sessionImages = SessionTestFixture.createSessionImages();
        int[] result = sessionImageRepository.saveAll(sessionImages);

        assertThat(result).hasSize(4);
        assertThat(result).containsExactlyInAnyOrder(1, 1, 1, 1);
    }

    @DisplayName("SessionId에 해당하는 모든 SessionImage를 가져온다")
    @Test
    void findAllSessionImageWithSessionId() {
        Images sessionImages = SessionTestFixture.createSessionImages(1L, 2L, 3L, 4L);
        int[] result = sessionImageRepository.saveAll(sessionImages);

        List<SessionImage> foundSessionImages = sessionImageRepository.findAllBySessionId(1L);

        List<Long> collectImageIds = foundSessionImages.stream()
            .map(SessionImage::getImageId)
            .collect(Collectors.toList());

        assertThat(foundSessionImages).hasSize(4);
        assertThat(collectImageIds).containsExactlyInAnyOrder(1L, 2L, 3L, 4L);
    }
}
