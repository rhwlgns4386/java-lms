package nextstep.sessions.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionStatusTest {
    @Test
    @DisplayName("수강 가능 상태 (신규 데이터)")
    void isValidStatusForApplication_validStatus_newData() {
        SessionStatus sessionStatus = SessionStatus.ofInitInstance();
        sessionStatus.startRecruiting();
        assertThatCode(() -> sessionStatus.isValidStatusForApplication()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("수강 불가 상태 (신규 데이터)")
    void isValidStatusForApplication_invalidStatus_newData() {
        SessionStatus sessionStatus = SessionStatus.ofInitInstance();
        assertThatThrownBy(() -> sessionStatus.isValidStatusForApplication()).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("기존 데이터 수강신청 가능 상태")
    void isValidStatusForApplication_validStatus_oldData() {
        SessionStatus sessionStatus = new SessionStatus(SessionStatusType.RECRUITING.getValue());
        assertTrue(sessionStatus.isRecruiting());
        assertThatCode(()-> sessionStatus.isValidStatusForApplication()).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("기존 데이터 수강신청 불가 상태")
    void isValidStatusForApplication_invalidStatus_oldData() {
        SessionStatus sessionStatus = new SessionStatus(SessionStatusType.PREPARING.getValue());
        assertThat(sessionStatus.isPreparing());
        assertThatThrownBy(() -> sessionStatus.isValidStatusForApplication()).isInstanceOf(RuntimeException.class);
    }


    @Test
    @DisplayName("모집중으로 상태 변경(기존데이터")
    void isValidStatusForApplication_startRecruiting_oldData() {
        SessionStatus sessionStatus = new SessionStatus(SessionStatusType.PREPARING.getValue());
        sessionStatus.startRecruiting();
        assertTrue(sessionStatus.isRecruiting());
    }

    @Test
    @DisplayName("모집중으로 상태 변경(신규데이터)")
    void isValidStatusForApplication_startRecruiting_newData() {
        SessionStatus sessionStatus = SessionStatus.ofInitInstance();
        sessionStatus.startRecruiting();
        assertTrue(sessionStatus.isRecruiting());
    }
}
