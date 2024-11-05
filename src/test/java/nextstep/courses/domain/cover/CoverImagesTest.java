package nextstep.courses.domain.cover;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CoverImagesTest {

    @Test
    void create() {
        CoverImages coverImages = CoverImages.of("src/test/java/nextstep/courses/domain/session/file/image.png");

        assertThat(coverImages).isEqualTo(
                CoverImages.of(new File("src/test/java/nextstep/courses/domain/session/file/image.png")));
    }

    @Test
    void add() {
        CoverImages coverImages = CoverImages.of("src/test/java/nextstep/courses/domain/session/file/image.png");
        CoverImage fileAdd = CoverImage.of(new File("src/test/java/nextstep/courses/domain/session/file/image.png"));
        CoverImage pathAdd = CoverImage.of("src/test/java/nextstep/courses/domain/session/file/image.png");

        coverImages.add(fileAdd);
        coverImages.add(pathAdd);

        assertThat(coverImages).isEqualTo(CoverImages.of(
                "src/test/java/nextstep/courses/domain/session/file/image.png",
                "src/test/java/nextstep/courses/domain/session/file/image.png",
                "src/test/java/nextstep/courses/domain/session/file/image.png"));
    }

    @Test
    void throw_exception_if_null_or_empty() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                CoverImages.of((File) null));
        assertThatIllegalArgumentException().isThrownBy(() ->
                CoverImages.of((String) null));
        assertThatIllegalArgumentException().isThrownBy(() ->
                CoverImages.of((List<String>) null));

        assertThatIllegalArgumentException().isThrownBy(() ->
                CoverImages.of(new ArrayList<>()));
        assertThatIllegalArgumentException().isThrownBy(() ->
                CoverImages.of(new String[0]));
        assertThatIllegalArgumentException().isThrownBy(() ->
                CoverImages.of(new File[0]));
    }
}
