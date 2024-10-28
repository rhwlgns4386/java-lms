package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nextstep.qna.domain.AnswerTest.A1;
import static nextstep.qna.domain.AnswerTest.A2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnswersTest {

    @DisplayName("답변 리스트를 삭제 할 수 있다.")
    @Test
    void deleteAll() throws CannotDeleteException {
        Answers answers = new Answers(List.of(A1));

        answers.deleteAll(NsUserTest.JAVAJIGI);

        assertThat(answers.getAnswers())
                .extracting(Answer::getId, Answer::isDeleted)
                .containsExactly(
                        Tuple.tuple(A1.getId(), true)
                );
    }

    @DisplayName("다른 사람의 답변이 있으면 예외가 발생한다.")
    @Test
    void deleteAllThrowingException() {
        Answers answers = new Answers(List.of(A1, A2));

        assertThatThrownBy(() -> answers.deleteAll(NsUserTest.JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class);
    }
}
