package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BaseTimeTest {

    @DisplayName("업데이트된 시간을 변경 할 수 있다.")
    @Test
    void create() {
        LocalDateTime createdAt = LocalDateTime.of(2024, 10, 10, 11, 10, 30);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 10, 10, 11, 10, 30);
        BaseTime baseTime = new BaseTime(createdAt, updatedAt);

        baseTime.updatedAt(LocalDateTime.of(2024, 11, 10, 11, 30, 30));

        assertThat(baseTime)
                .extracting("updatedAt")
                .isEqualTo(LocalDateTime.of(2024, 11, 10, 11, 30, 30));
    }

    @DisplayName("생성시간 이전으로 업데이트 시간을 바꿀 수 없다.")
    @Test
    void invalidUpdatedAt() {
        LocalDateTime createdAt = LocalDateTime.of(2024, 10, 10, 11, 10, 30);
        BaseTime baseTime = new BaseTime(createdAt, null);

        assertThatThrownBy(() -> baseTime.updatedAt(LocalDateTime.of(2023, 11, 10, 11, 30, 30)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
