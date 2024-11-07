package nextstep.courses.domain.coverimage;

import nextstep.courses.dto.MultipartFile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionCoverImagesTest {

    @Test
    void create() {
        List<MultipartFile> multipartFiles = List.of(
                new MultipartFile("aaa.jpg", 300, 200, 1 * 1024 * 1024L),
                new MultipartFile("bbb.jpg", 300, 200, 1 * 1024 * 1024L),
                new MultipartFile("ccc.jpg", 300, 200, 1 * 1024 * 1024L)
        );
        SessionCoverImages sessionCoverImages = SessionCoverImages.create(1L, multipartFiles);

        assertThat(sessionCoverImages.getSize()).isEqualTo(3);
    }
}
