package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nextstep.courses.domain.SessionDateTest.sd1;
import static nextstep.courses.domain.SessionImageTest.si1;

public class SessionTest {

    @Test
    @DisplayName("유저가 포함되지 않았으면 false를 반환한다.")
    void 유저_불포함() {
        Session session = new FreeSession("1", sd1, si1, SessionStatus.RECRUITING, List.of(NsUserTest.SANJIGI));
        Assertions.assertThat(session.isContainUser(NsUserTest.JAVAJIGI)).isFalse();
    }

    @Test
    @DisplayName("유저가 포함되어 있으면 true를 반환한다.")
    void 유저_포함() {
        Session session = new FreeSession("1", sd1, si1, SessionStatus.RECRUITING, List.of(NsUserTest.SANJIGI));
        Assertions.assertThat(session.isContainUser(NsUserTest.SANJIGI)).isTrue();
    }
}
