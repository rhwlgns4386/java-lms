package nextstep.sessions.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

public class SessionStatusTest {
    @ParameterizedTest
    @ValueSource(strings = {"01","03"})
    void isValidStatusForApplication_invalidStatus(String testStatus) {
        SessionStatus sessionStatus = new SessionStatus(SessionStatusEnum.getEnumByStatus(testStatus));

        assertThatThrownBy(() -> sessionStatus.isValidStatusForApplication()).isInstanceOf(RuntimeException.class);
    }

    @Test
    void isValidStatusForApplication_validStatus() {
        SessionStatus sessionStatus = new SessionStatus(SessionStatusEnum.RECRUITING);
        assertThatCode(()-> sessionStatus.isValidStatusForApplication()).doesNotThrowAnyException();
    }

    @Test
    void isValidStatusForApplicationNew_validStatus() {
        SessionStatus sessionStatus = new SessionStatus(SessionProgressStatusEnum.PREPARING, SessionRecruitmentStatusEnum.NON_RECRUITING);
        assertThatThrownBy(() -> sessionStatus.isValidStatusForApplicationNew()).isInstanceOf(RuntimeException.class);
    }

    @Test
    void isValidStatusForApplicationNew_invaliddStatus() {
        SessionStatus sessionStatus = new SessionStatus(SessionProgressStatusEnum.PROGRESSING, SessionRecruitmentStatusEnum.RECRUITING);
        assertThatCode(()-> sessionStatus.isValidStatusForApplicationNew()).doesNotThrowAnyException();
    }
}
