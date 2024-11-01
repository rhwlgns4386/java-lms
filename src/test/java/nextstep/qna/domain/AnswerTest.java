package nextstep.qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static nextstep.users.domain.NsUserTest.SANJIGI;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AnswerTest {
    public static final Answer A1 = new Answer(JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    @DisplayName("로그인 한 유저가 답변 작성자인지 테스트")
    public void isOwner() {
        assertThat(A1.isOwner(JAVAJIGI)).isTrue();
        assertThat(A2.isOwner(JAVAJIGI)).isFalse();
    }

    @Test
    @DisplayName("답변 삭제 테스트")
    public void deleteAnswer() {
        assertAll(
                () -> assertThat(A1.deleteAnswer(JAVAJIGI)).isEqualTo(new DeleteHistory(ContentType.ANSWER, null, JAVAJIGI, LocalDateTime.now())),
                () -> assertThat(A1.isDeleted()).isTrue(),
                () -> assertThat(A2.deleteAnswer(JAVAJIGI)).isEqualTo(null),
                () -> assertThat(A2.isDeleted()).isFalse()
        );

    }

}
