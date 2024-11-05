package nextstep.courses.infrastructure;

import nextstep.courses.domain.image.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class ImageRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepositoryTest.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ImageRepository imageRepository;

    @BeforeEach
    void setUp() {
        imageRepository = new JdbcImageRepository(namedParameterJdbcTemplate);
    }

    @Test
    void crud() {
        ImageSize imageSize = new ImageSize(500);
        ImagePixel imagePixel = new ImagePixel(300, 200);
        Image image = new Image(imageSize, ImageType.JPG, imagePixel);
        int count = imageRepository.save(image, 1L);
        assertThat(count).isEqualTo(1);
        Image saveImage = imageRepository.findById(1L).orElseThrow();
        assertThat(image.getImagePixel()).isEqualTo(saveImage.getImagePixel());
        assertThat(image.getImageType()).isEqualTo(saveImage.getImageType());
        assertThat(image.getImageSize()).isEqualTo(saveImage.getImageSize());
        LOGGER.debug("Image: {}", saveImage);
    }

    @Test
    void findBySessionId() {
        long sessionId = 1L;
        ImageSize imageSize = new ImageSize(500);
        ImagePixel imagePixel = new ImagePixel(300, 200);
        Image image = new Image(imageSize, ImageType.JPG, imagePixel);
        int count = imageRepository.save(image, sessionId);
        assertThat(count).isEqualTo(1);
        count = imageRepository.save(image, sessionId);
        assertThat(count).isEqualTo(1);

        List<Image> images = imageRepository.findAllBySessionId(1L);
        assertThat(images).hasSize(2);
    }
}
