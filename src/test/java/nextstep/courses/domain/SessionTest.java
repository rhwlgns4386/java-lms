package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

public class SessionTest {

    @Test
    void 수강신청시_강의금액과_같지않으면_예외() {
        Session session = TestSessionFactory.createTestSession(500);
        assertThatIllegalArgumentException().isThrownBy(() -> session.enrollment(new Charge(499), NsUserTest.JAVAJIGI));
    }

}
