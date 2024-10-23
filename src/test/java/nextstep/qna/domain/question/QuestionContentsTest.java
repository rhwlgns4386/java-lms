package nextstep.qna.domain.question;

import nextstep.qna.domain.answer.Answers;
import nextstep.qna.domain.answer.Comments;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionContentsTest {

    @Test
    void create() {
        Comments comments = new Comments(new NsUser(), "comments");
        Answers answers = new Answers();
        assertThat(new QuestionContents("title", comments, answers)).isEqualTo(new QuestionContents("title", comments, answers));
    }
}
