package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;

public class FreeSessionTest {

    @Test
    void 생성() {
        FreeSession freeSession = SessionTest.createFreeSession(SessionStatus.PREPARE);
        assertThat(freeSession).isEqualTo(SessionTest.createFreeSession(SessionStatus.PREPARE));
    }

    @Test
    void 모집중이_아니면_등록하지_못한다() {
        FreeSession freeSession = SessionTest.createFreeSession(SessionStatus.PREPARE);

        assertThatThrownBy(() -> freeSession.addStudent(NsUserTest.GYEONGJAE))
            .isInstanceOf(IllegalStateException.class);
    }
}
