package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

public class AnswersTest {

    @Test
    public void delete_답변_중_다른_사람이_쓴_글() throws Exception {
        Answers answers = new Answers(AnswerTest.A1, AnswerTest.A2);
        assertThatThrownBy(() -> {
            answers.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }
}
