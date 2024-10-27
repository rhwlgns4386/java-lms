package nextstep.qna.domain.answer;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.BaseEntity;
import nextstep.qna.domain.DeleteHistory.ContentType;
import nextstep.qna.domain.DeleteHistory.DeleteHistory;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswersTest {

    @Test
    void create() {
        assertThat(new Answers(List.of())).isEqualTo(new Answers());
    }

    @Test
    void 답변_추가() {
        Answers answers = new Answers();

        answers.add(new Answer(NsUserTest.JAVAJIGI, "content"));

        assertThat(answers).isEqualTo(new Answers(List.of(new Answer(NsUserTest.JAVAJIGI, "content"))));
    }

    @Test
    void 댓글_삭제_테스트() {
        Answers answers = new Answers(List.of(new Answer(NsUserTest.JAVAJIGI, "content")));

        answers.deleteAnswer(NsUserTest.JAVAJIGI);

        assertThat(answers).isEqualTo(new Answers(List.of(new Answer(NsUserTest.JAVAJIGI, "content", true))));
    }

    @Test
    void 히스토리_테스트() {
        Answers answers = new Answers(List.of(new Answer(1L, NsUserTest.JAVAJIGI, "content")));

        List<DeleteHistory> deleteHistories = answers.toDeleteHistories();

        assertThat(deleteHistories).isEqualTo(List.of(new DeleteHistory(ContentType.ANSWER, 1L, NsUserTest.JAVAJIGI, LocalDateTime.now())));
    }

}
