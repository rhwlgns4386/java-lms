package nextstep.courses.domain.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionApplyTest {


    @Test
    void equals() {
        SessionApply apply = SessionApply.create(1L, 1L);
        assertThat(apply).isEqualTo(SessionApply.create(1L, 1L));
    }

    @DisplayName("GUEST(선발되지 않은 사람)면 취소 가능")
    @Test
    void applyCancel() {
        SessionApply apply = new SessionApply(1L, 1L, 1L, true, false);

        apply.cancel();

        assertThat(apply.isDeleted()).isTrue();
    }

    @DisplayName("GUEST(선발되지 않은 사람)면 수강 승인 시 예외")
    @Test
    void applyCancel_exception() {
        SessionApply apply = new SessionApply(1L, 1L, 1L, false, false);

        assertThatThrownBy(
                () -> apply.cancel()
        );
    }


}
