package nextstep.courses.domain.session.sessioncoverimage;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageTypeTest {

    @Test
    @DisplayName("찾고자 하는 파일 유형을 반환하며, 허용되지 않은 파일유형일 경우 IllegalArgumentException이 발생한다.")
    void find_By_File_Type() {
        Assertions.assertThatThrownBy(() -> {
            ImageType.findByFileType("docs");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
