package nextstep.courses.domain;

import nextstep.fixture.SessionDateCreator;
import nextstep.fixture.SessionImageCreator;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SessionTest {

    @Test
    @DisplayName("유저가 포함되지 않았으면 false를 반환한다.")
    void 유저_불포함() {
        Session session = new FreeSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), SessionStatus.RECRUITING, List.of(2L));
        Assertions.assertThat(session.isContainUser(NsUserTest.JAVAJIGI)).isFalse();
    }

    @Test
    @DisplayName("유저가 포함되어 있으면 true를 반환한다.")
    void 유저_포함() {
        Session session = new FreeSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), SessionStatus.RECRUITING, List.of(2L));
        Assertions.assertThat(session.isContainUser(NsUserTest.SANJIGI)).isTrue();
    }
}
