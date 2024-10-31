package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    void 답변만_삭제_테스트_성공() throws CannotDeleteException {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answer answer = new Answer(NsUserTest.JAVAJIGI, question, "Answers Contents1");

        DeleteHistory result = answer.deleted(NsUserTest.JAVAJIGI);
        assertThat(answer.isDeleted()).isTrue();
        assertThat(result).isEqualTo(new DeleteHistory(ContentType.ANSWER, null, NsUserTest.JAVAJIGI, LocalDateTime.now()));
    }

    @Test
    void 답변만_삭제_테스트_실패() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answer answer = new Answer(NsUserTest.JAVAJIGI, question, "Answers Contents1");

        assertThatThrownBy(() -> answer.deleted(NsUserTest.SANJIGI))
                .isInstanceOf(CannotDeleteException.class);
    }
}
