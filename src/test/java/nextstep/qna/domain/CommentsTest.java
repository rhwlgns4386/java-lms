package nextstep.qna.domain;

import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentsTest {

    @Test
    void create() {
        assertThat(new Comments(new NsUser(), "comments")).isEqualTo(new Comments(new NsUser(), "comments"));
    }
}
