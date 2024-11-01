package nextstep.sessions.domain;

import nextstep.sessions.CannotRegisterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SessionStatusTest {

    @DisplayName("강의 상태가 모집중이 아닐 때 수강신청할 경우 CannotRegisterException 예외를 발생시킨다.")
    @CsvSource(value = {"ENDED", "PREPARING"})
    @ParameterizedTest(name = "수강 신청 불가능한 강의 상태: {0}")
    void isSessionRegistrationAvailable(SessionStatus given) {
        assertThatThrownBy(given::checkRegisterAvailable)
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("강의 수강 신청은 강의 상태가 모집중일 때만 가능합니다.");
    }
}
