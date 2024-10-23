package nextstep.qna.domain;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    void create() {
        BaseEntity baseEntity = new BaseEntity(1L);
        Answers answers = new Answers();
        QuestionContents questionContents = new QuestionContents("title", new Comments(new NsUser(), "comments"), answers);
        assertThat(new Question(baseEntity, questionContents, false)).isEqualTo(new Question(baseEntity, questionContents, false));
    }

    @Test
    void 작성자가_자신인지_판별() {
        NsUser loginUser = new NsUser();
        BaseEntity baseEntity = new BaseEntity(1L);
        QuestionContents questionContents = new QuestionContents("title", new Comments(loginUser, "comments"), new Answers());
        Question question = new Question(baseEntity, questionContents, false);

        question.throwExceptionIfOwner(loginUser);
    }

    @Test
    void 삭제_상태_변경_테스트() {
        BaseEntity baseEntity = new BaseEntity(1L);
        QuestionContents questionContents = new QuestionContents("title", new Comments(new NsUser(), "comments"), new Answers());
        Question question = new Question(baseEntity, questionContents, false);

        Question expected = question.delete();

        assertThat(expected).isEqualTo(new Question(baseEntity, questionContents, true));
    }
}
