package nextstep.qna.domain.deletehistory;

import nextstep.qna.domain.BaseEntity;
import nextstep.qna.domain.DeleteHistory.ContentType;
import nextstep.qna.domain.DeleteHistory.DeleteHistory;
import nextstep.qna.domain.answer.Answer;
import nextstep.qna.domain.answer.Answers;
import nextstep.qna.domain.answer.Comments;
import nextstep.qna.domain.question.Question;
import nextstep.qna.domain.question.QuestionContents;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteHistoryTest {

    @Test
    void 히스토리_추가() {
        List<Answer> answers = List.of(new Answer(2L, NsUserTest.JAVAJIGI, "contents"), new Answer(3L, NsUserTest.JAVAJIGI, "contents"));
        Question question = new Question(1L, NsUserTest.JAVAJIGI, "title", "contents", false, answers);

        List<DeleteHistory> expected = DeleteHistory.addHistory(question);

        assertThat(expected).isEqualTo(List.of(new DeleteHistory(ContentType.QUESTION, 1L, NsUserTest.JAVAJIGI, LocalDateTime.now()), new DeleteHistory(ContentType.ANSWER, 2L, NsUserTest.JAVAJIGI, LocalDateTime.now()), new DeleteHistory(ContentType.ANSWER, 3L, NsUserTest.JAVAJIGI, LocalDateTime.now())));
    }
}
