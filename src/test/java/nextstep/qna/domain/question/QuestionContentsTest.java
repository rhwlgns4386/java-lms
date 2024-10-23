package nextstep.qna.domain.question;

import nextstep.qna.domain.answer.Answers;
import nextstep.qna.domain.answer.Comments;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionContentsTest {

    @Test
    void create() {
        assertThat(new QuestionContents(NsUserTest.JAVAJIGI, "title", "comments", List.of())).isEqualTo(new QuestionContents(NsUserTest.JAVAJIGI, "title", "comments", List.of()));
    }
}
