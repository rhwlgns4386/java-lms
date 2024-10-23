package nextstep.qna.domain;

import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionCommentsTest {

    @Test
    void create() {
        assertThat(new QuestionComments("title", "comments", new NsUser())).isEqualTo(new QuestionComments("title", "comments", new NsUser()));
    }
}
