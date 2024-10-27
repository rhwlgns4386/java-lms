package nextstep.qna.domain.answer;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentsTest {

    @Test
    void create() {
        assertThat(new Comments(NsUserTest.JAVAJIGI, "comments")).isEqualTo(new Comments(NsUserTest.JAVAJIGI, "comments"));
    }
}
