package nextstep.qna.domain.question;

import nextstep.qna.domain.BaseEntity;
import nextstep.qna.domain.answer.Answers;
import nextstep.qna.domain.answer.Comments;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    void create() {
        assertThat(new Question(NsUserTest.JAVAJIGI, "title", "contents")).isEqualTo(new Question(NsUserTest.JAVAJIGI, "title", "contents"));
    }

    @Test
    void 작성자가_자신인지_판별() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title", "contents");

        question.throwExceptionIfOwner(NsUserTest.JAVAJIGI);
    }

    @Test
    void 삭제_상태_변경_테스트() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title", "contents");

        Question expected = question.delete();

        assertThat(expected).isEqualTo(new Question(NsUserTest.JAVAJIGI, "title", "contents", true));
    }
}
