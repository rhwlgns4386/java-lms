package nextstep.qna.domain.question;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.BaseEntity;
import nextstep.qna.domain.DeleteHistory.ContentType;
import nextstep.qna.domain.DeleteHistory.DeleteHistory;
import nextstep.qna.domain.answer.Answer;
import nextstep.qna.domain.answer.Answers;
import nextstep.qna.domain.answer.Comments;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    void create() {
        assertThat(new Question(NsUserTest.JAVAJIGI, "title", "contents")).isEqualTo(new Question(NsUserTest.JAVAJIGI, "title", "contents"));
    }

    @Test
    void 삭제_성공_테스트() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title", "contents");

        question.delete(NsUserTest.JAVAJIGI);

        assertThat(question).isEqualTo(new Question(NsUserTest.JAVAJIGI, "title", "contents", true));
    }

    @Test
    void 삭제_실패_테스트() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title", "contents");

        assertThatThrownBy(() -> {
            question.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    void 히스토리_테스트() {
        Question question = new Question(1L, NsUserTest.JAVAJIGI, "title", "contents", false, List.of());

        List<DeleteHistory> expected = question.toDeleteHistories();

        assertThat(expected).isEqualTo(List.of(new DeleteHistory(ContentType.QUESTION, 1L, NsUserTest.JAVAJIGI, LocalDateTime.now())));
    }
}
