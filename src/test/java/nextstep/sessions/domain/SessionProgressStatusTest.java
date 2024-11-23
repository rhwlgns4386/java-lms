package nextstep.sessions.domain;

import nextstep.sessions.CannotRegisterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionProgressStatusTest {

    @DisplayName("강의 상태가 모집중이 아닐 때 수강신청할 경우 CannotRegisterException 예외를 발생시킨다.")
    @CsvSource(value = {"ENDED", "PREPARING"})
    @ParameterizedTest(name = "수강 신청 불가능한 강의 상태: {0}")
    void throw_CannotRegisterException_when_sessionRegistration_is_unavailable(SessionProgressStatus given) {
        assertThatThrownBy(given::checkRegisterAvailable)
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("강의 진행 상태가 '진행 중'이 아닐 때 강의 수강 신청이 불가합니다.");
    }

    @DisplayName("이미 저장되어있는 강의 상태 칼럼 데이터 값과 강의 진행 상태 코드값이 호환된다.")
    @Test
    void return_SessionProgressStatus_when_dbData_is_old_sessionStatus_value() {
        String oldSessionStatusData = "RECRUITING";

        SessionProgressStatus actual = SessionProgressStatus.of(oldSessionStatusData);

        assertThat(actual).isEqualTo(SessionProgressStatus.ONGOING);
    }
}
