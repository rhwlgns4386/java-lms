package nextstep.sessions.domain;

import nextstep.sessions.CannotRegisterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionRecruitmentStatusTest {

    @DisplayName("강의 모집 상태가 모집 중이 않을 때 수강신청할 경우 CannotRegisterException 예외를 발생시킨다.")
    @Test
    void throw_CannotRegisterException_when_sessionRegistration_is_unavailable() {
        SessionRecruitmentStatus given = SessionRecruitmentStatus.CLOSED;

        assertThatThrownBy(given::checkRegisterAvailable)
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("강의 모집 상태가 '모집 중'이 아닐 때 강의 수강 신청이 불가합니다.");
    }
}
