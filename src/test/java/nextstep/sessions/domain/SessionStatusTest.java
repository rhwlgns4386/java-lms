package nextstep.sessions.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

public class SessionStatusTest {
    @Test
    void isValidStatusForApplicationNew_validStatus() {
        SessionStatus sessionStatus = new SessionStatus(SessionProgressStatusEnum.PREPARING, SessionRecruitmentStatusEnum.NON_RECRUITING);
        assertThatThrownBy(() -> sessionStatus.isValidStatusForApplication()).isInstanceOf(RuntimeException.class);
    }

    @Test
    void isValidStatusForApplicationNew_invalidStatus() {
        SessionStatus sessionStatus = new SessionStatus(SessionProgressStatusEnum.PROGRESSING, SessionRecruitmentStatusEnum.RECRUITING);
        assertThatCode(()-> sessionStatus.isValidStatusForApplication()).doesNotThrowAnyException();
    }
}
