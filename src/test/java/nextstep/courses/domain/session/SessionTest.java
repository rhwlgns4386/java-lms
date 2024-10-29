package nextstep.courses.domain.session;

import nextstep.courses.domain.coverimage.SessionCoverImageTest;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {

    @Test
    void 수강신청시_모집중_아니면_예외() {
        Payment payment = new Payment("1", 1L, NsUserTest.JAVAJIGI.getId(), 0L);
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 28, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 30, 23, 59);

        Session session = Session.createFree(SessionCoverImageTest.IMAGE, startDate, endDate);


        assertThatThrownBy(
                () -> session.registration(NsUserTest.JAVAJIGI, payment)
        );
    }

    @Test
    void 수강신청시_정원마감이면_예외() {
        Payment payment = new Payment("1", 1L, NsUserTest.JAVAJIGI.getId(), 3000L);
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 28, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 30, 23, 59);

        Session session = Session.createPaid(SessionCoverImageTest.IMAGE, startDate, endDate, 3000L, 1);

        session.recruiting();

        session.registration(NsUserTest.JAVAJIGI, payment);

        assertThatThrownBy(
                () -> session.registration(NsUserTest.SANJIGI, payment)
        );
    }

}
