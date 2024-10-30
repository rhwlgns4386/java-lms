package nextstep.users.domain;

import nextstep.qna.UnAuthorizedException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NsUserTest {
    public static final NsUser JAVAJIGI = new NsUser(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final NsUser SANJIGI = new NsUser(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    @Test
    public void 유저_아이디_일치_확인() {
        assertThat(JAVAJIGI.matchUser(SANJIGI)).isFalse();
        assertThat(JAVAJIGI.matchUser(new NsUser(3L, "javajigi", "another_password", "other_name", "other@slipp.net"))).isTrue();
    }

    @Test
    public void 유저_비밀번호_일치_확인() {
        assertThat(JAVAJIGI.matchPassword("password")).isTrue();
        assertThat(JAVAJIGI.matchPassword("wrong_password")).isFalse();
    }

    @Test
    public void 유저_이름과_이메일_일치_확인() {
        NsUser sameInfoUser = new NsUser(3L, "differentUser", "password", "name", "javajigi@slipp.net");
        assertThat(JAVAJIGI.equalsNameAndEmail(sameInfoUser)).isTrue();
    }

    @Test
    public void 유저_이름과_이메일_불일치() {
        assertThat(JAVAJIGI.equalsNameAndEmail(SANJIGI)).isFalse();
    }

    @Test
    public void 유저_업데이트_성공() {
        NsUser updatedUser = new NsUser(1L, "javajigi", "password", "updatedName", "updated@slipp.net");

        JAVAJIGI.update(JAVAJIGI, updatedUser);

        assertThat(JAVAJIGI.getName()).isEqualTo("updatedName");
        assertThat(JAVAJIGI.getEmail()).isEqualTo("updated@slipp.net");
    }

    @Test
    public void 유저_업데이트_실패_아이디_불일치() {
        assertThatThrownBy(() -> JAVAJIGI.update(SANJIGI, JAVAJIGI))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @Test
    public void 유저_업데이트_실패_비밀번호_불일치() {
        NsUser wrongPasswordUser = new NsUser(1L, "javajigi", "wrong_password", "name", "javajigi@slipp.net");

        assertThatThrownBy(() -> JAVAJIGI.update(JAVAJIGI, wrongPasswordUser))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @Test
    public void 게스트_유저_확인() {
        NsUser guestUser = JAVAJIGI.GUEST_USER;
        assertThat(guestUser.isGuestUser()).isTrue();
        assertThat(JAVAJIGI.isGuestUser()).isFalse();
    }
}
