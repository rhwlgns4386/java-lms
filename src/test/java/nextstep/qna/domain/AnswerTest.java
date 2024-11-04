package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    @DisplayName("질문자와 답변자가 같은 경우 답변을 삭제할 수 있다")
    void delete_질문자와_답변자가_같은경우() throws CannotDeleteException {
        NsUser loginUser = NsUserTest.JAVAJIGI;

        A1.delete(loginUser);
        Assertions.assertThat(A1.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("질문자와 답변자가 다른 경우 답변을 삭제할 수 없다")
    void delete_질문자와_답변자가_다른경우() {
        NsUser loginUser = NsUserTest.JAVAJIGI;

        Assertions.assertThatThrownBy(() -> {
            A2.delete(loginUser);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("답변 삭제 시 삭제 히스토리를 반환한다")
    void delete_toDeleteHistory() throws CannotDeleteException {
        NsUser loginUser = NsUserTest.JAVAJIGI;
        DeleteHistory deleteHistory = A1.delete(loginUser);

        Assertions.assertThat(deleteHistory).isNotNull();
    }
}
