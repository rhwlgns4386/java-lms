package nextstep.courses.domain.session;

import nextstep.courses.SessionException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionApplyStatusTest {

    @Test
    void 지원_불가_예외_발생() {
        SessionApplyStatus applyStatus = new SessionApplyStatus(SessionProgressStatus.READY, SessionRecruitStatus.NON_RECRUITMENT);

        assertThatThrownBy(
                () -> applyStatus.validate()
        ).isInstanceOf(SessionException.class);
    }
}
