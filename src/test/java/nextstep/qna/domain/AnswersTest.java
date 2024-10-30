package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswersTest {

    @Test
    void create() {
        Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");
        Answers answers = new Answers(Arrays.asList(A1, A2));
        assertThat(answers).isEqualTo(new Answers(Arrays.asList(A1, A2)));
    }

    @Test
    void deleteAll_성공() throws CannotDeleteException {
        Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        Answer A2 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents2");
        Answers answers = new Answers(Arrays.asList(A1, A2));
        answers.deleteAll(NsUserTest.JAVAJIGI);
        assertThat(A1.isDeleted()).isTrue();
        assertThat(A2.isDeleted()).isTrue();
    }

    @Test
    void deleteAll_실패() throws CannotDeleteException {
        Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");
        Answers answers = new Answers(Arrays.asList(A1, A2));
        assertThatThrownBy(() -> {
            answers.deleteAll(NsUserTest.JAVAJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    void 삭제_이력_추가() throws CannotDeleteException {
        Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
        Answer A2 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents2");
        Answers answers = new Answers(Arrays.asList(A1, A2));

        assertThat(answers.deleteAll(NsUserTest.JAVAJIGI)).isEqualTo(new DeleteHistories(Arrays.asList(
                new DeleteHistory(ContentType.ANSWER, A1.getId(), A1.getWriter(), LocalDateTime.now()),
                new DeleteHistory(ContentType.ANSWER, A2.getId(), A2.getWriter(), LocalDateTime.now())
        )));
    }
}
