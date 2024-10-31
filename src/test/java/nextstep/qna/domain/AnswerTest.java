package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @DisplayName("게시글 작성자와 답변의 작성자가 다를 경우 예외")
    @Test
    void 게시글_작성자와_답변의_작성자_다름_예외() {
        assertThatThrownBy(() -> A1.delete(NsUserTest.SANJIGI)).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("삭제 시 상태 확인 및 히스토리 객체 검증")
    void delete() throws CannotDeleteException {
        DeleteHistory deleteHistory = A1.delete(NsUserTest.JAVAJIGI);

        DeleteHistory history = new DeleteHistory(ContentType.ANSWER, A1.getId(), NsUserTest.JAVAJIGI, LocalDateTime.now());

        assertAll(
                () -> assertThat(deleteHistory).isEqualTo(history),
                () -> assertThat(A1.isDeleted()).isTrue()
        );
    }

}
