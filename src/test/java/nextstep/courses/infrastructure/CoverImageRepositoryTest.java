package nextstep.courses.infrastructure;

import nextstep.courses.domain.cover.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class CoverImageRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CoverImageRepository coverImageRepository;
    private CoverImage coverImage;
    private CoverImageFile file;
    private CoverImageType type;
    private CoverImageSize size;

    @BeforeEach
    void setUp() {
        coverImageRepository = new JdbcCoverImageRepository(jdbcTemplate);
        file = new CoverImageFile(1024 * 100);
        type = CoverImageType.JPG;
        size = new CoverImageSize(600, 400);
        coverImage = new CoverImage(file, type, size);
    }

    @Test
    void save() {
        Long seq = coverImageRepository.save(coverImage);

        assertThat(seq).isNotNull();
    }

    @Test
    void findBy() {
        Long seq = coverImageRepository.save(coverImage);

        CoverImage foundCoverImage = coverImageRepository.findById(seq);

        assertThat(foundCoverImage)
                .extracting("file.size", "type", "imageSize.width", "imageSize.height")
                .containsExactly(file.getSize(), type, size.getWidth(), size.getHeight());
    }

    @Test
    void findByNonExistentSessionId() {
        assertThatThrownBy(() -> coverImageRepository.findById(999L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM cover_image");
    }
}
