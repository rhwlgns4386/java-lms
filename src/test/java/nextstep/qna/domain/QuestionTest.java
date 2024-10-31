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
    void isOwnerCheck_CannotDeleteException() {
        assertThatThrownBy(() -> Q1.validateOwnerCheck(NsUserTest.SANJIGI)).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("삭제처리 true 반환")
    void deleteQuestion() throws CannotDeleteException {
        Q1.deleteQuestion(NsUserTest.JAVAJIGI);
        assertThat(Q1.isDeleted()).isTrue();
    }
}
