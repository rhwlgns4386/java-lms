package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.DeleteHistory;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");
    private NsUser writer;
    private NsUser otherUser;
    private Question question;
    private Answer answer1;
    private Answer answer2;

    @BeforeEach
    void setUp() {
        writer = new NsUser(1L, "writer", "password", "name");
        otherUser = new NsUser(2L, "otherUser", "password", "name");

        question = new Question(writer, "Title", "Contents");
        answer1 = new Answer(1L, writer, question, "Answer Contents 1");
        answer2 = new Answer(2L, writer, question, "Answer Contents 2");

        question.addAnswer(answer1);
        question.addAnswer(answer2);
    }

    @Test
    @DisplayName("질문 작성자 본인이 삭제 요청하면 삭제가 성공한다")
    void deleteQuestionByOwner() throws CannotDeleteException {
        List<DeleteHistory> deleteHistories = question.delete(writer);

        assertThat(question.isDeleted()).isTrue();
        assertThat(deleteHistories).hasSize(3);
    }

    @Test
    @DisplayName("질문 작성자가 아닌 사용자가 삭제 요청 시 예외가 발생한다")
    void deleteQuestionByNonOwner() {
        assertThatThrownBy(() -> question.delete(otherUser))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("질문을 삭제할 권한이 없습니다.");
    }

    @Test
    @DisplayName("답변 중 다른 사용자가 작성한 답변이 있을 때 삭제가 불가능하다")
    void deleteQuestionWithOtherUserAnswer() {
        Answer otherAnswer = new Answer(3L, otherUser, question, "Other User Answer");
        question.addAnswer(otherAnswer);

        assertThatThrownBy(() -> question.delete(writer))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }
}
