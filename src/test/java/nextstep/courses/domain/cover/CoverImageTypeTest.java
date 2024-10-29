package nextstep.courses.domain.cover;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CoverImageTypeTest {
    @DisplayName("이미지 타입은 gif,jpg,jpeg,png,svg만 허용한다")
    @ParameterizedTest
    @ValueSource(strings = {"gif", "jpg", "jpeg", "png", "svg"})
    void create(String type) {
        CoverImageType coverImageType = CoverImageType.getCoverImageType(type);

        assertThat(coverImageType).isNotNull();
    }

    @DisplayName("허용하지 않은 이미지 타입은 예외처리한다.")
    @Test
    void create_NotAllowedType() {
        assertThatThrownBy(() -> CoverImageType.getCoverImageType("jpng"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
