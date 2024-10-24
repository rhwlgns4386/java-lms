package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AnswersTest {
    private static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");

    @Test
    @DisplayName("성공 - add 메서드가 답변을 추가한다.")
    void addTest() {
        Answer answer = new Answer(Q1.getWriter(), Q1, "답변1");
        Answers answers = new Answers();
        answers.add(answer);

        assertThat(answers.getAnswers()).hasSize(1);
        assertThat(answers.getAnswers()).containsExactly(answer);
    }

    @Test
    @DisplayName("실패 - validateExistOtherUser 메서드가 작성자가 아닌 다른 사람의 답변이 존재할 때 예외가 발생한다.")
    void validateExistOtherUserTest() {
        Answer answer1 = new Answer(Q1.getWriter(), Q1, "답변1");
        Answer answer2 = new Answer(NsUserTest.SANJIGI, Q1, "답변2");
        Answers answers = new Answers();
        answers.add(answer1);
        answers.add(answer2);

        assertThatThrownBy(() -> answers.validateExistOtherUser(NsUserTest.JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessage("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    @Test
    @DisplayName("실패 - getAnswers 메서드의 반환된 List를 수정했을 때 예외가 발생한다.")
    void throwExceptionWhenModifyingReturnedDeleteAnswerList() {
        Answers answers = new Answers();
        assertThatThrownBy(() -> answers.getAnswers().add(
                new Answer(Q1.getWriter(), Q1, "답변1")))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}