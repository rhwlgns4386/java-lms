package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    @DisplayName("로그인 사용자와 질문한 사람이 같은 경우 체크로직 삭제 가능 PASS")
    void isOwnerCheck() throws CannotDeleteException {
        Q1.validateOwnerCheck(NsUserTest.JAVAJIGI);
    }

    @Test
    @DisplayName("로그인 사용자와 질문한 사람이 다른 경우 삭제 불가")
    void validateOwnerCheck_CannotDeleteException() {
        assertThatThrownBy(() -> Q1.validateOwnerCheck(NsUserTest.SANJIGI)).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("질문 삭제처리 호출하면 true 반환하고 이력을 리턴한다.")
    void deleteQuestion() throws CannotDeleteException {
        DeleteHistorys deleteHistorys = Q1.deleteQuestion(NsUserTest.JAVAJIGI);

        assertThat(Q1.isDeleted()).isTrue();
        assertThat(deleteHistorys.getDeleteHistories().get(0).getDeletedBy()).isEqualTo(NsUserTest.JAVAJIGI);
    }

    @Test
    @DisplayName("질문과 답변 삭제를 하면 이력이 2row 생성되고 질문 답변 삭제되는 전반적 테스트")
    void detleteQuestionAndAnswer() throws CannotDeleteException {
        Answer answer = new Answer(2L, NsUserTest.SANJIGI, QuestionTest.Q2, "Answers Contents1");
        Q2.addAnswer(answer);

        DeleteHistorys deleteHistorys = Q2.detleteQuestionAndAnswer(NsUserTest.SANJIGI);

        assertThat(Q2.isDeleted()).isTrue();
        assertThat(answer.isDeleted()).isTrue();
        assertThat(deleteHistorys.getDeleteHistories()).hasSize(2);

    }
}
