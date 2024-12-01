package nextstep.courses.domain;

import static nextstep.courses.domain.SessionStatus.ENROLLING;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import nextstep.courses.factory.SessionFactory;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

public class SessionTest {

    @Test
    void 수강신청시_강의금액과_같지않으면_예외() {
        Session session = createTestSession(500);
        assertThatIllegalArgumentException().isThrownBy(() -> session.enrollment(new Charge(499), NsUserTest.JAVAJIGI));
    }

    Session createTestSession(int charge) {
        return SessionFactory.paidSession(charge, 0, ENROLLING, "test", 300, 200, 100, ImageType.JPEG, LocalDate.now(),
                LocalDate.now());
    }
}
