package nextstep.courses.domain;

import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static nextstep.courses.domain.SessionDateTest.sd1;
import static nextstep.courses.domain.SessionImageTest.si1;
import static nextstep.courses.domain.SessionStatus.END;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FreeSessionTest {

    public static final FreeSession fs1 = new FreeSession("1", sd1, si1, END, new ArrayList<>());

    @Test
    @DisplayName("모집중이 아닌 경우, 예외를 발생시킨다.")
    void 모집중이아닌경우_강의신청불가() {
        assertThatThrownBy(() -> fs1.enroll(NsUser.GUEST_USER))
                .isInstanceOf(IllegalStateException.class);
    }

}
