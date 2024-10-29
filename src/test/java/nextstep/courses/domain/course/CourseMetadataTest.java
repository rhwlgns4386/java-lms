package nextstep.courses.domain.course;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CourseMetadataTest {
    @DisplayName("이름이 없는 경우엔 예외로 처리한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void create(String title) {
        assertThatThrownBy(() -> {
            new CourseMetadata(1L, title, 1L);

        }).isInstanceOf(IllegalArgumentException.class);
    }
}
