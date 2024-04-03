package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.answer.Answer;
import nextstep.qna.domain.answer.Answers;
import nextstep.qna.domain.question.Question;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static nextstep.qna.domain.answer.Answers.ANSWER_CAN_NOT_BE_DELETED;
import static nextstep.qna.domain.question.Question.NOT_PERMISSION_DELETE_QUESTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    private static Question Q1;
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @BeforeEach
    public void setup() {
        Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    }

    @Test
    @DisplayName("질문 작성자와 제목, 내용을 입력 받으면" +
        "Question을 생성할 수 있으며" +
        "Question 정상 생성되었는지 검증")
    void questionTest() {
        String givenTitle = "질문입니다.";
        String givenContents = "내용입니다.";
        Question question = new Question(NsUserTest.JAVAJIGI, givenTitle, givenContents);
        assertThat(question.getWriter()).isEqualTo(NsUserTest.JAVAJIGI);
    }

    @Test
    @DisplayName("질문 작성자와 로그인 사용자가 다른 경우" +
        "Question 삭제 불가능 테스트")
    void questionTest2() {
        assertThatThrownBy(() -> {
            Q1.delete(NsUserTest.SANJIGI, new Answers());
        }).isInstanceOf(CannotDeleteException.class)
            .hasMessageContaining(NOT_PERMISSION_DELETE_QUESTION);
    }

    @Test
    @DisplayName("질문 작성자와 로그인 사용자는 같지만" +
        "답변 글의 답변자가 다른 경우" +
        "Question 삭제 불가능 테스트")
    void questionTest3() {
        Answers answers = new Answers();
        answers.add(new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents1"));

        assertThatThrownBy(() -> {
            Q1.delete(NsUserTest.JAVAJIGI, answers);
        }).isInstanceOf(CannotDeleteException.class)
            .hasMessageContaining(ANSWER_CAN_NOT_BE_DELETED);
    }

    @Test
    @DisplayName("질문 작성자와 로그인 사용자는 같고" +
        "답변이 없는 경우" +
        "Question 삭제 테스트")
    void questionTest4() throws CannotDeleteException {
        Q1.delete(NsUserTest.JAVAJIGI, new Answers());
        assertThat(Q1.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("질문 작성자와 로그인 사용자가 같고" +
        "답변 글의 모든 답변자가 같은 경우" +
        "Question 삭제 테스트")
    void questionTest5() throws CannotDeleteException {
        Answers answers = new Answers();
        answers.add(new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1"));
        Q1.delete(NsUserTest.JAVAJIGI, answers);
        assertThat(Q1.isDeleted()).isTrue();
    }
}
