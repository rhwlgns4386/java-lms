package nextstep.courses.domain.coverimage;

import nextstep.qna.CoverImageException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class SessionCoverImageTypeTest {

    @Test
    void 일치하는_확장자_찾기() {
        assertAll(
                () -> assertThat(SessionCoverImageType.search("GIF")).isEqualTo(SessionCoverImageType.GIF),
                () -> assertThat(SessionCoverImageType.search("gif")).isEqualTo(SessionCoverImageType.GIF)
        );
    }

    @Test
    void 허용하지_않는_확장자_입력시_예외() {
        assertThatThrownBy(() -> SessionCoverImageType.search("exe")).isInstanceOf(CoverImageException.class);
    }

}
