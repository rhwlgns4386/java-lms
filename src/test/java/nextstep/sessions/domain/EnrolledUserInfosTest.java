package nextstep.sessions.domain;

import nextstep.registration.domain.SessionRegistrationInfo;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class EnrolledUserInfosTest {

    @Test
    void 등록실패_이미_등록() {
        Session session = new Session(1L, SessionImageTest.IMAGE, PeriodTest.PERIOD, SessionTypeTest.FREE_TYPE);
        EnrolledUserInfos infos = new EnrolledUserInfos();
        infos.add(new SessionRegistrationInfo(session, NsUserTest.JAVAJIGI));
        assertThat(infos.isAlreadyEnrolled(NsUserTest.JAVAJIGI)).isTrue();
    }
}
