package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswersTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    private List<Answer> sameAnswers = new ArrayList<>();
    private List<Answer> diffAnswers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        sameAnswers.add(A1);
        sameAnswers.add(A1);
        diffAnswers.add(A1);
        diffAnswers.add(A2);
    }

    @Test
    @DisplayName("다른 사람이 쓴 답변이 없는 경우 삭제 가능 PASS")
    void isOwnerCheck() throws CannotDeleteException {
        Answers answers = new Answers(sameAnswers);

        answers.validateOwnerCheck(NsUserTest.JAVAJIGI);
    }

    @Test
    @DisplayName("다른 사람이 쓴 답변이 없는 경우 삭제 불가 체크")
    void isOwnerCheck_CannotDeleteException() {
        Answers answers = new Answers(diffAnswers);

        assertThatThrownBy(() -> answers.validateOwnerCheck(NsUserTest.JAVAJIGI)).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("삭제처리 true 반환")
    void deleteQuestion(){
        A1.deleteAnswer();
        assertThat(A1.isDeleted()).isTrue();
    }

}
