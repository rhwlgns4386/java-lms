package nextstep.courses.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import nextstep.courses.domain.Image;
import nextstep.courses.domain.ImageRepository;

@JdbcTest
public class ImageRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ImageRepository imageRepository;

    @BeforeEach
    void setUp() {
        imageRepository = new JdbcImageRepository(jdbcTemplate);
    }

    @DisplayName("Session id로 해당 강의 찾는다")
    @Test
    void findById() {
        Optional<Image> image1 = imageRepository.findById(1L);
        Optional<Image> image2 = imageRepository.findById(2L);
        Optional<Image> image3 = imageRepository.findById(3L);
        assertThat(image1.isPresent()).isTrue();
        assertThat(image2.isPresent()).isTrue();
        assertThat(image3.isPresent()).isTrue();
    }
}
