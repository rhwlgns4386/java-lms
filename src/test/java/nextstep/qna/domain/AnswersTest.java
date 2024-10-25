package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AnswersTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    public void 질문자와답변글의_작성자가_다른경우_실패테스트() {
        Answers answers = new Answers(List.of(A1, A2));

        assertThatThrownBy(() -> {
            answers.isOwner(NsUserTest.JAVAJIGI);
        })
                .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    public void 질문자와답변글의_작성자가_같은경우_성공테스트() throws CannotDeleteException {
        Answers answers = new Answers(List.of(A1, A1));
        assertDoesNotThrow(() -> {
            answers.isOwner(NsUserTest.JAVAJIGI);
        });
    }


}
