package nextstep.qna.domain.answer;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.BaseEntity;
import nextstep.qna.domain.DeleteHistory.ContentType;
import nextstep.qna.domain.DeleteHistory.DeleteHistory;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerTest {

    @Test
    void create() {
        assertThat(new Answer(NsUserTest.JAVAJIGI, "contents")).isEqualTo(new Answer(NsUserTest.JAVAJIGI, "contents"));
    }

    @Test
    void 답변_삭제_성공() {
        Answer answer = new Answer(NsUserTest.JAVAJIGI, "contents");

        answer.delete(NsUserTest.JAVAJIGI);

        assertThat(answer).isEqualTo(new Answer(NsUserTest.JAVAJIGI, "contents", true));
    }

    @Test
    void 답변_삭제_실패() {
        Answer answer = new Answer(NsUserTest.JAVAJIGI, "contents");

        assertThatThrownBy(() -> {
            answer.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    void 히스토리_테스트() {
        Answer answer = new Answer(1L, NsUserTest.JAVAJIGI, "contents");

        DeleteHistory deleteHistory = answer.toDeleteHistory();

        assertThat(deleteHistory).isEqualTo(new DeleteHistory(ContentType.ANSWER, 1L, NsUserTest.JAVAJIGI, LocalDateTime.now()));
    }
}
