package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    void 삭제_가능_질문_작성자_답변_작성자_일치() throws CannotDeleteException {
        A1.delete(NsUserTest.JAVAJIGI);
        assertThat(A1.isDeleted()).isTrue();
    }

    @Test
    void 삭제_불가_질문_작성자_답변_작성자_불일치() {
        assertThatThrownBy(() -> {
            A1.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    void 삭제_이력_생성() throws CannotDeleteException {
        Answer A3 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers contents3");
        assertThat(A3.delete(NsUserTest.JAVAJIGI)).isEqualTo(new DeleteHistory(ContentType.ANSWER, A3.getId(), A3.getWriter(), LocalDateTime.now()));
    }
}
