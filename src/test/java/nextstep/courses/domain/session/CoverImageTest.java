package nextstep.courses.domain.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.io.File;

import org.junit.jupiter.api.Test;

public class CoverImageTest {

    public static final String TEST_FILE_PATH = "src/test/java/nextstep/courses/domain/session/file/";

    @Test
    void create() {
        CoverImage coverImage = CoverImage.of(getFilePath("image.png"));

        assertThat(coverImage)
            .isEqualTo(CoverImage.of(new File(getFilePath("image.png"))));
    }

    private String getFilePath(String filename) {
        return new File(TEST_FILE_PATH, filename).getAbsolutePath();
    }

    @Test
    void throw_exception_if_null_or_empty_file_path() {
        assertThatIllegalArgumentException().isThrownBy(() -> CoverImage
                .of((String)null));
        assertThatIllegalArgumentException().isThrownBy(() -> CoverImage
                .of(""));
    }

    @Test
    void throw_exception_if_null_file() {
        assertThatIllegalArgumentException().isThrownBy(() -> CoverImage
                .of((File)null));
    }

    @Test
    void throw_exception_if_invalid_file_extension() {
        assertThatIllegalArgumentException().isThrownBy(() -> CoverImage
            .of(getFilePath("image.tif")));
    }

    @Test
    void throw_exception_if_not_enough_height_or_width() {
        assertThatIllegalArgumentException().isThrownBy(() -> CoverImage
            .of(getFilePath("150w.png")));
        assertThatIllegalArgumentException().isThrownBy(() -> CoverImage
            .of(getFilePath("150h.png")));
    }

    @Test
    void throw_exception_if_3_2_ratio_width_height() {
        assertThatIllegalArgumentException().isThrownBy(() -> CoverImage
            .of(getFilePath("invalid_ratio.png")));
    }

    @Test
    void throw_exception_if_exceed_file_size() {
        assertThatIllegalArgumentException().isThrownBy(() -> CoverImage
            .of(getFilePath("1mb.jpg")));
    }
}
