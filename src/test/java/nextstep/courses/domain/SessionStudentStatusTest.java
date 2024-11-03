package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionStudentStatusTest {

    @DisplayName("선발된 인원인지 확인한다")
    @Test
    void checkPassStatus() {
        boolean result1 = SessionStudentStatus.checkPass(SessionStudentStatus.PASS);
        boolean result2 = SessionStudentStatus.checkPass(SessionStudentStatus.FAIL);

        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @DisplayName("문자열의 값을 SessionStudentStatus로 변환한다")
    @Test
    void toSessionStudentStatus() {
        SessionStudentStatus studentStatus = SessionStudentStatus.from("pass");
        assertThat(studentStatus).isEqualTo(SessionStudentStatus.PASS);
    }
}
