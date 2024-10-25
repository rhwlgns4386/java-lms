package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AnswersTest {

    @Test
    void 답변_추가_하기_테스트() {
        Question question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answer answer = new Answer(NsUserTest.JAVAJIGI, question, "Answers Contents1");

        Answers answers = new Answers();
        answers.add(answer);

        assertThat(answers).isEqualTo(new Answers(List.of(new Answer(NsUserTest.JAVAJIGI, question, "Answers Contents1"))));
    }

    @Test
    void 답변_삭제_리스트_추가_테스트() throws CannotDeleteException {
        Answers answers = new Answers();

        answers.makeAnswersDelete(NsUserTest.JAVAJIGI, List.of(new DeleteHistory(ContentType.ANSWER, null, NsUserTest.JAVAJIGI, LocalDateTime.now())));

    }
}
