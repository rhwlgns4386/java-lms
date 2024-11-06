package nextstep.courses.domain.session;

import nextstep.courses.SessionException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {

    @Test
    void 수강신청시_모집중_아니면_예외() {
        Payment payment = new Payment("1", 1L, NsUserTest.JAVAJIGI.getId(), 0L);
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 28, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 30, 23, 59);

        Session session = Session.createFree(1L, new SessionPeriod(startDate, endDate));


        assertThatThrownBy(
                () -> session.apply(payment)
        ).isInstanceOf(SessionException.class);
    }

    @Test
    void 수강신청시_정원마감이면_예외() {
        Payment payment = new Payment("1", 1L, NsUserTest.JAVAJIGI.getId(), 3000L);
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 28, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 30, 23, 59);
        SessionPeriod sessionPeriod = new SessionPeriod(startDate, endDate);

        Session session = Session.createPaid(1L, 3000L, sessionPeriod, 1);
        session.recruiting();

        session.mapping(List.of(new SessionStudent(3L, 1L)));

        assertThatThrownBy(
                () -> session.apply(payment)
        ).isInstanceOf(SessionException.class);
    }

}
