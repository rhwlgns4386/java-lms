package nextstep.courses.domain.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class SessionApplyTest {


    @Test
    void equals() {
        SessionApply apply = SessionApply.create(1L, 1L);
        assertThat(apply).isEqualTo(SessionApply.create(1L, 1L));
    }

    @DisplayName("GUEST(선발되지 않은 사람)면 취소 가능")
    @Test
    void cancelApply() {
        SessionApply apply = new SessionApply(1L, 1L, 1L, true, false, false);

        apply.cancel();

        assertThat(apply.isDeleted()).isTrue();
    }

    @DisplayName("GUEST(선발되지 않은 사람)면 수강 승인 시 예외")
    @Test
    void cancelApply_exception() {
        SessionApply apply = new SessionApply(1L, 1L, 1L, false, false, false);

        assertThatThrownBy(
                () -> apply.cancel()
        );
    }

    @DisplayName("GUEST(선발되지 않은 사람)면 수강 승인 시 예외")
    @Test
    void submitApply_exception() {
        SessionApply apply = new SessionApply(1L, 1L, 1L, true, false, false);

        assertThatThrownBy(
                () -> apply.submit()
        );
    }

    @DisplayName("GUEST가 아니면(선발된 회원)면 수강 가능")
    @Test
    void submitApply() {
        SessionApply apply = new SessionApply(1L, 1L, 1L, false, false, false);

        apply.submit();

        assertAll(
                () -> assertThat(apply.isSubmit()).isTrue(),
                () -> assertThat(apply.isDeleted()).isTrue()
        );

    }

}
