package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    @DisplayName("질문 상태를 삭제 상태로 변경")
    void deleteQuestion() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        assertThat(question.isDeleted()).isFalse();
        question.delete();
        assertThat(question.isDeleted()).isTrue();
    }
}
