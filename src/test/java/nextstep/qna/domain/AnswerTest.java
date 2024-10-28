package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnswerTest {
    private static final LocalDateTime ANSWER_DELETED_AT = LocalDateTime.of(2024, 10, 27, 10, 0, 0);

    private NsUser writer;
    private Question question;
    private Answer answer;

    @BeforeEach
    public void setUp() {
        writer = new NsUser(1L, "moon", "1234", "moonyoonji", "moon@a.com");
        question = new Question(writer, "title", "question contents", LocalDateTime.of(2024, 10, 27, 9, 0, 0));
        answer = new Answer(writer, question, "answer contents", LocalDateTime.of(2024, 10, 27, 9, 10, 0));
    }

    @DisplayName("게시글의 답변을 삭제하면 DeleteHistory 객체를 생성하여 반환한다.")
    @Test
    void delete_success_and_return_DeleteHistory() throws CannotDeleteException {
        DeleteHistory actual = answer.delete(writer, ANSWER_DELETED_AT);

        assertTrue(answer.isDeleted());
        assertThat(actual)
                .extracting("deletedBy", "contentType")
                .contains(writer, ContentType.ANSWER);
    }

    @DisplayName("로그인한 사용자와 답변의 작성자가 다르면 답변을 삭제할 수 없다.")
    @Test
    void delete_fail_writer_is_not_same_as_loginUser() {
        NsUser nonWriter = new NsUser(2L, "sun", "5678", "sunyoonji", "sun@a.com");

        assertThatThrownBy(() -> answer.delete(nonWriter, ANSWER_DELETED_AT))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("답변을 삭제할 권한이 없습니다.");
    }
}
