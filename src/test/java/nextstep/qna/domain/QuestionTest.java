package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    @DisplayName("답변이 추가되는지 확인")
    void addAnswer() {
        Answer answer = new Answer(NsUserTest.JAVAJIGI, Q1, "answer1");
        QuestionTest.Q1.addAnswer(answer);

        assertThat(QuestionTest.Q1.getAnswers().getAnswers().size());
        assertThat(QuestionTest.Q1.getAnswers().getAnswers().contains(answer)).isTrue();
    }

    @Test
    @DisplayName("글쓴이가 맞는지 확인(맞는 경우)")
    void isOwner_trueCase() {
        assertThat(QuestionTest.Q1.isOwner(NsUserTest.JAVAJIGI)).isTrue();
    }

    @Test
    @DisplayName("글쓴이가 맞는지 확인(아닌 경우)")
    void isOwner_falseCase() {
        assertThat(QuestionTest.Q1.isOwner(NsUserTest.SANJIGI)).isFalse();
    }

    @Test
    @DisplayName("질문이 삭제되는지 확인")
    void deleteQuestion() throws CannotDeleteException {
        List<DeleteHistory> deleteHistories = QuestionTest.Q1.deleteQuestion(NsUserTest.JAVAJIGI);

        assertThat(QuestionTest.Q1.isDeleted()).isTrue();
        assertThat(deleteHistories.isEmpty()).isFalse();
        assertThat(QuestionTest.Q1.getAnswers().getAnswers().size()).isEqualTo(0);
    }
}
