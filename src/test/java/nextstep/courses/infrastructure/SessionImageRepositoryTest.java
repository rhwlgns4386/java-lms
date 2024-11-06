package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionImageRepository;
import nextstep.courses.domain.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionImageRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionImageRepository sessionImageRepository;

    @BeforeEach
    void setUp() {
        sessionImageRepository = new JdbcSessionImageRepository(jdbcTemplate);
    }

    @DisplayName("강의 이미지 정보를 저장하고 id(pk)로 조회한다")
    @Test
    void save() {
        Size size = new Size(300, 200);
        SessionImage sessionImage = new SessionImage(10000L, "/test_image.jpg",100, size, 1L, LocalDateTime.now(), LocalDateTime.now());

        sessionImageRepository.save(sessionImage);

        SessionImage sessionImageById = sessionImageRepository.findById(10000L);
        assertThat(sessionImageById).extracting("sessionId", "filePath")
                .containsExactlyInAnyOrder(10000L, "/test_image.jpg");
    }

    @DisplayName("하나의 강의에 하나 이상의 이미지 등록이 가능하다")
    @Test
    void saveMultiImage() {
        Size size = new Size(300, 200);
        SessionImage image1 = new SessionImage(999L, "/image_thumbnail.jpg",100, size, 1L, LocalDateTime.now(), LocalDateTime.now());
        SessionImage image2 = new SessionImage(999L, "/image.jpg",100, size, 1L, LocalDateTime.now(), LocalDateTime.now());

        sessionImageRepository.save(image1);
        sessionImageRepository.save(image2);

        List<SessionImage> images = sessionImageRepository.findBySessionId(999L);
        assertThat(images).hasSize(2);
    }
}
