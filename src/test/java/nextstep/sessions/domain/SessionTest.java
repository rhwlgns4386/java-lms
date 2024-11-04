package nextstep.sessions.domain;

import nextstep.courses.domain.CourseTest;
import nextstep.registration.domain.SessionRegistrationInfo;
import nextstep.sessions.exception.CannotEnrollException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {

    @Test
    void 수강신청성공_무료() {
        Session session = new Session(1L, PeriodTest.PERIOD, SessionTypeTest.FREE_TYPE);
        Payment payment = new Payment("202410261140", 1L, NsUserTest.JAVAJIGI.getId(), 0L);
        session.updateToRecruiting();
        session.enroll(NsUserTest.JAVAJIGI, payment);
        assertThat(session.getEnrolledUserInfosSize()).isEqualTo(1);
    }

    @Test
    void 수강신청성공_유료() {
        Session session = new Session(1L, PeriodTest.PERIOD, SessionTypeTest.PAID_TYPE);
        Payment payment = new Payment("202410261140", 1L, NsUserTest.JAVAJIGI.getId(), 1000L);
        session.updateToRecruiting();
        session.enroll(NsUserTest.JAVAJIGI, payment);
        assertThat(session.getEnrolledUserInfosSize()).isEqualTo(1);
    }

    @Test
    void 수강신청실패_모집중_아닌_경우() {
        Session session = new Session(1L, PeriodTest.PERIOD, SessionTypeTest.FREE_TYPE);
        Payment payment = new Payment("202410261140", 1L, NsUserTest.JAVAJIGI.getId(), 0L);
        assertThatThrownBy(() -> session.enroll(NsUserTest.JAVAJIGI, payment))
                .isInstanceOf(CannotEnrollException.class);
    }

    @Test
    void 수강신청실패_이미_신청() {
        Session session = new Session(1L, PeriodTest.PERIOD, SessionTypeTest.FREE_TYPE);
        Payment payment = new Payment("202410261140", 1L, NsUserTest.JAVAJIGI.getId(), 0L);
        session.updateToRecruiting();
        session.enroll(NsUserTest.JAVAJIGI, payment);
        assertThatThrownBy(() -> session.enroll(NsUserTest.JAVAJIGI, payment))
                .isInstanceOf(CannotEnrollException.class);
    }

    @Test
    void 수강신청실패_인원_초과() {
        Session session = new Session(1L, new CoverImages(), PeriodTest.PERIOD, SessionTypeTest.PAID_TYPE, CourseTest.COURSE);
        Payment payment1 = new Payment("202410261140", 1L, NsUserTest.JAVAJIGI.getId(), 1000L);
        Payment payment2 = new Payment("202410261141", 1L, NsUserTest.SANJIGI.getId(), 1000L);
        session.updateToRecruiting();
        SessionRegistrationInfo info = session.enroll(NsUserTest.JAVAJIGI, payment1);
        NsUserTest.JAVAJIGI.approveSessionStudent(info);
        assertThatThrownBy(() -> session.enroll(NsUserTest.SANJIGI, payment2))
                .isInstanceOf(CannotEnrollException.class);
    }

    @Test
    void 수강신청실패_금액_불일치() {
        Session session = new Session(1L, PeriodTest.PERIOD, SessionTypeTest.PAID_TYPE);
        Payment payment = new Payment("202410261140", 1L, NsUserTest.JAVAJIGI.getId(), 500L);
        session.updateToRecruiting();
        assertThatThrownBy(() -> session.enroll(NsUserTest.JAVAJIGI, payment))
                .isInstanceOf(CannotEnrollException.class);
    }
}
