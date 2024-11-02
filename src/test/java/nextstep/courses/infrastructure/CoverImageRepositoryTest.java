package nextstep.courses.infrastructure;

import nextstep.courses.domain.cover.CoverImageRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class CoverImageRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoverImageRepositoryTest.class);

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
        int seq = coverImageRepository.save(coverImage);

        assertThat(seq).isEqualTo(1);
    }

    @Test
    void findBy() {
        int seq = coverImageRepository.save(coverImage);

        CoverImage foundCoverImage = coverImageRepository.findById((long) seq);

        assertThat(foundCoverImage.getFile().getSize()).isEqualTo(file.getSize());
        assertThat(foundCoverImage.getType()).isEqualTo(type);
        assertThat(foundCoverImage.getImageSize().getWidth()).isEqualTo(size.getWidth());
        assertThat(foundCoverImage.getImageSize().getHeight()).isEqualTo(size.getHeight());
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
