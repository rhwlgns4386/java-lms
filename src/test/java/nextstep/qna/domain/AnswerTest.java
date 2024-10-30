package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.NotFoundException;
import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    private NsUser writer;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        writer = new NsUser(1L, "user1", "password", "name");
        question = new Question(writer, "Question Title", "Question Contents");
        answer = new Answer(writer, question, "Answer Contents");
    }

    @Test
    @DisplayName("답변 삭제 시 deleted 상태가 true로 변경되고, 삭제 이력이 반환된다")
    void deleteAnswer() throws CannotDeleteException {
        DeleteHistory deleteHistory = answer.delete(writer);

        assertThat(answer.isDeleted()).isTrue();
        assertThat(deleteHistory).isNotNull();
    }

    @Test
    @DisplayName("작성자가 동일한 경우 isOwner가 true를 반환한다")
    void isOwner_withCorrectWriter() {
        assertThat(answer.isOwner(writer)).isTrue();
    }

    @Test
    @DisplayName("작성자가 다른 경우 isOwner가 false를 반환한다")
    void isOwner_withDifferentWriter() {
        NsUser anotherUser = new NsUser(2L, "user2", "password", "anotherName");
        assertThat(answer.isOwner(anotherUser)).isFalse();
    }

    @Test
    @DisplayName("작성자가 null인 경우 예외가 발생한다")
    void createAnswerWithNullWriter() {
        assertThatThrownBy(() -> new Answer(null, question, "Answer Contents"))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @Test
    @DisplayName("질문이 null인 경우 예외가 발생한다")
    void createAnswerWithNullQuestion() {
        assertThatThrownBy(() -> new Answer(writer, null, "Answer Contents"))
                .isInstanceOf(NotFoundException.class);
    }
}
