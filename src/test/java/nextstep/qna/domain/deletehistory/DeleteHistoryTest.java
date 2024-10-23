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
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteHistoryTest {

    @Test
    void 히스토리_추가() {
        NsUser loginUser = new NsUser();
        LocalDateTime now = LocalDateTime.now();
        BaseEntity baseEntity = new BaseEntity(1L, now, now);
        Answers answers = new Answers(List.of(new Answer(baseEntity, new Comments(loginUser, "title1")), new Answer(baseEntity, new Comments(loginUser, "title1"))));
        QuestionContents questionContents = new QuestionContents("title", new Comments(new NsUser(), "comments"), answers);
        Question question = new Question(baseEntity, questionContents, false);

        List<DeleteHistory> expected = DeleteHistory.addHistory(question);

        assertThat(expected).isEqualTo(List.of(new DeleteHistory(ContentType.QUESTION, 1L, loginUser, now), new DeleteHistory(ContentType.ANSWER, 1L, loginUser, now), new DeleteHistory(ContentType.ANSWER, 1L, loginUser, now)));
    }
}
