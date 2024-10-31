package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static nextstep.qna.domain.AnswerTest.A1;
import static nextstep.qna.domain.AnswerTest.A2;
import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static nextstep.users.domain.NsUserTest.SANJIGI;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class QuestionTest {
    public static final Question Q1 = new Question(JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    @DisplayName("로그인 한 유저가 질문 작성자인지 테스트")
    public void isOwner() {
        assertThat(Q1.isOwner(JAVAJIGI)).isTrue();
        assertThat(Q1.isOwner(SANJIGI)).isFalse();
    }

    @Test
    @DisplayName("다른 사람이 쓴 답변이 있는지 테스트")
    public void hasOtherUserAnswer() {
        Q1.addAnswer(A1);
        assertThat(Q1.hasOtherUserAnswer(JAVAJIGI)).isFalse();

        Q1.addAnswer(A2);
        assertThat(Q1.hasOtherUserAnswer(JAVAJIGI)).isTrue();
    }

    @Test
    @DisplayName("질문 삭제 테스트")
    public void deleteQuestion() {
        Q1.addAnswer(A1);
        assertAll(
                () -> assertThat(Q1.deleteQuestion().get(0)).isEqualTo(new DeleteHistory(ContentType.QUESTION, 0L, JAVAJIGI, LocalDateTime.now())),
                () -> assertThat(Q1.isDeleted()).isTrue(),
                () -> assertThat(Q1.deleteQuestion().get(1)).isEqualTo(new DeleteHistory(ContentType.ANSWER, null, JAVAJIGI, LocalDateTime.now())),
                () -> assertThat(Q1.getAnswers().get(0).isDeleted()).isTrue()
        );
    }

}
