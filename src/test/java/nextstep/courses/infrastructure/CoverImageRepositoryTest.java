package nextstep.courses.infrastructure;

import nextstep.courses.domain.CourseRepository;
import nextstep.courses.domain.CoverImageRepository;
import nextstep.courses.domain.course.Course;
import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImageFile;
import nextstep.courses.domain.cover.CoverImageSize;
import nextstep.courses.domain.cover.CoverImageType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class CoverImageRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoverImageRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CoverImageRepository coverImageRepository;

    @BeforeEach
    void setUp() {
        coverImageRepository = new JdbcCoverImageRepository(jdbcTemplate);

//        jdbcTemplate.update(
//                "insert into session (id, title, status, created_at) values (?, ?, ?, ?)",
//                1L, "테스트 세션", "OPEN", LocalDateTime.now()
//        );
    }

    @Test
    void crud() {
        // given
        CoverImageFile file = new CoverImageFile(1024 * 100);  // 100KB
        CoverImageType type = CoverImageType.JPG;
        CoverImageSize size = new CoverImageSize(600, 400);
        CoverImage coverImage = new CoverImage(file, type, size);

        // when
        int count = coverImageRepository.save(1L, coverImage);
        assertThat(count).isEqualTo(1);

        CoverImage savedCoverImage = coverImageRepository.findBySessionId(1L);

        // then
        assertThat(savedCoverImage.getFile().getSize()).isEqualTo(file.getSize());
        assertThat(savedCoverImage.getType()).isEqualTo(type);
        assertThat(savedCoverImage.getImageSize().getWidth()).isEqualTo(size.getWidth());
        assertThat(savedCoverImage.getImageSize().getHeight()).isEqualTo(size.getHeight());

        LOGGER.debug("CoverImage: {}", savedCoverImage);
    }

    @Test
    void findByNonExistentSessionId() {
        assertThatThrownBy(() -> coverImageRepository.findBySessionId(999L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("delete from cover_image");
//        jdbcTemplate.update("delete from session");
    }
}
