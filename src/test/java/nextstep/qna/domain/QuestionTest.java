package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionTest {
    private NsUser writer;
    private Question question;

    @BeforeEach
    public void setUp() {
        writer = new NsUser(1L, "moon", "1234", "moonyoonji", "moon@a.com");
        question = new Question(writer, "title", "question contents", LocalDateTime.of(2024, 10, 27, 9, 0, 0));
    }

    @DisplayName("게시글을 삭제하면 DeleteHistory 객체를 생성하여 반환한다.")
    @Test
    void delete_success_and_return_DeleteHistory() throws Exception {
        List<DeleteHistory> actual = question.delete(writer, LocalDateTime.of(2024, 10, 27, 10, 0, 0));

        assertTrue(question.isDeleted());
        assertThat(actual)
                .extracting("deletedBy", "contentType")
                .containsExactly(tuple(writer, ContentType.QUESTION));
    }

    @DisplayName("로그인한 사용자와 질문자가 다르면 질문글을 삭제할 수 없다.")
    @Test
    void delete_fail_loginUser_is_not_question_writer() {
        NsUser nonWriter = new NsUser(2L, "sun", "5678", "sunyoonji", "sun@a.com");

        assertThatThrownBy(() -> question.delete(nonWriter, LocalDateTime.of(2024, 10, 27, 10, 0, 0)))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("질문을 삭제할 권한이 없습니다.");
    }
}
