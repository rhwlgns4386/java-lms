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
    void 삭제_가능_로그인사용자_질문자_일치() throws CannotDeleteException {
        Q1.delete(NsUserTest.JAVAJIGI);
        assertThat(Q1.isDeleted()).isTrue();
    }

    @Test
    void 삭제_불가_로그인사용자_질문자_불일치() {
        assertThatThrownBy(() -> {
            Q1.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    void 삭제_가능_답변_없음() throws CannotDeleteException {
        Q2.delete(NsUserTest.SANJIGI);
        assertThat(Q2.isDeleted()).isTrue();
    }

    @Test
    void 삭제_가능_질문자_답변자_일치() throws CannotDeleteException {
        Question Q3 = new Question(NsUserTest.JAVAJIGI, "title3", "contents3");
        Answer A1 = new Answer(NsUserTest.JAVAJIGI, Q3, "Answers Contents1");
        Q3.addAnswer(A1);
        Q3.delete(NsUserTest.JAVAJIGI);
        assertThat(Q3.isDeleted()).isTrue();
    }

    @Test
    void 삭제_불가_질문자_답변자_불일치() {
        Question Q4 = new Question(NsUserTest.JAVAJIGI, "title4", "contents4");
        Answer A2 = new Answer(NsUserTest.SANJIGI, Q4, "Answers Contents2");
        Q4.addAnswer(A2);

        assertThatThrownBy(() -> {
            Q4.delete(NsUserTest.JAVAJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    void 삭제_이력_생성() throws CannotDeleteException {
        Question Q5 = new Question(NsUserTest.JAVAJIGI, "title5", "contents5");
        assertThat(Q5.delete(NsUserTest.JAVAJIGI)).isEqualTo(new DeleteHistories(List.of(
                new DeleteHistory(ContentType.QUESTION, Q5.getId(), Q5.getWriter(), LocalDateTime.now())
        )));
    }
}
