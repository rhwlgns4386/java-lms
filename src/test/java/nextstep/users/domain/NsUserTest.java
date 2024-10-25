package nextstep.users.domain;

import nextstep.qna.CannotDeleteException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

public class NsUserTest {
    public static final NsUser JAVAJIGI = new NsUser(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final NsUser SANJIGI = new NsUser(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");


    @Test
    public void isOwner테스트() throws CannotDeleteException {
        assertThatThrownBy(() ->
        {
            JAVAJIGI.isOwner(SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    public void isOwner성공테스트() throws CannotDeleteException {
        assertDoesNotThrow(() ->
        {
            JAVAJIGI.isOwner(JAVAJIGI);
        });
    }
}
