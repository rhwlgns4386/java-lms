package nextstep.users.domain;

import nextstep.courses.domain.CourseTest;
import nextstep.payments.domain.Payment;
import nextstep.registration.domain.RegistrationStatus;
import nextstep.registration.domain.SessionRegistrationInfo;
import nextstep.sessions.domain.PeriodTest;
import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.SessionTypeTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NsUserTest {
    public static final NsUser JAVAJIGI = new NsUser(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final NsUser SANJIGI = new NsUser(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    @Test
    void approveStudent() {
        Session session = new Session(1L, null, PeriodTest.PERIOD, SessionTypeTest.FREE_TYPE, CourseTest.COURSE);
        Payment payment = new Payment("202410261140", 1L, NsUserTest.SANJIGI.getId(), 0L);
        session.updateToRecruiting();
        SessionRegistrationInfo info = session.enroll(NsUserTest.SANJIGI, payment);
        JAVAJIGI.approveSessionStudent(info);
        assertThat(info.getRegistrationStatus()).isEqualTo(RegistrationStatus.APPROVED);
    }

    @Test
    void rejectStudent() {
        Session session = new Session(1L, null, PeriodTest.PERIOD, SessionTypeTest.FREE_TYPE, CourseTest.COURSE);
        Payment payment = new Payment("202410261140", 1L, NsUserTest.SANJIGI.getId(), 0L);
        session.updateToRecruiting();
        SessionRegistrationInfo info = session.enroll(NsUserTest.SANJIGI, payment);
        JAVAJIGI.rejectSessionStudent(info);
        assertThat(info.getRegistrationStatus()).isEqualTo(RegistrationStatus.REJECTED);
    }
}
