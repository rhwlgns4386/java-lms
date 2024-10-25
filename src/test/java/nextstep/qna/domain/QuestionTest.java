package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    void 질문만_삭제_테스트_코드_작성() throws CannotDeleteException {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        List<DeleteHistory> histories = question.deleted(NsUserTest.JAVAJIGI);

        assertThat(question.isDeleted()).isTrue();
        assertThat(histories).isEqualTo(List.of(new DeleteHistory(ContentType.QUESTION, 0L, NsUserTest.JAVAJIGI, LocalDateTime.now())));
    }

    @Test
    void 질문만_삭제_테스트_실패_코드_작성() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");

        assertThat(question.isDeleted()).isFalse();
        assertThatThrownBy(() -> question.deleted(NsUserTest.SANJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageMatching("질문을 삭제할 권한이 없습니다.");
    }

    @Test
    void 질문_답변_삭제_테스트_성공_코드_작성() throws CannotDeleteException {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answer answer = new Answer(11L, NsUserTest.JAVAJIGI, Q1, "Answers Contents1");

        question.addAnswer(answer);

        List<DeleteHistory> histories = question.deleted(NsUserTest.JAVAJIGI);

        assertThat(question.isDeleted()).isTrue();
        assertThat(answer.isDeleted()).isTrue();
        assertThat(histories).isEqualTo(List.of(new DeleteHistory(ContentType.QUESTION, 0L, NsUserTest.JAVAJIGI, LocalDateTime.now()), new DeleteHistory(ContentType.ANSWER, 11L, NsUserTest.JAVAJIGI, LocalDateTime.now())));
    }

    @Test
    void 질문_답변_삭제_테스트_실패_코드_작성() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answer answer = new Answer(11L, NsUserTest.SANJIGI, Q1, "Answers Contents1");

        question.addAnswer(answer);

        assertThat(question.isDeleted()).isFalse();
        assertThat(answer.isDeleted()).isFalse();
        assertThatThrownBy(() -> question.deleted(NsUserTest.JAVAJIGI))
                .isInstanceOf(CannotDeleteException.class)
                .hasMessageMatching("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

}
