package nextstep.qna.domain.answer;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.BaseEntity;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswersTest {

    @Test
    void create() {
        assertThat(new Answers(List.of())).isEqualTo(new Answers());
    }

    @Test
    void 작성자가_자신인_경우_테스트() {
        Answers answers = new Answers(List.of(new Answer(NsUserTest.JAVAJIGI, "content"), new Answer(NsUserTest.JAVAJIGI, "content")));

        answers.throwExceptionIfOwner(NsUserTest.JAVAJIGI);
    }

    @Test
    void 작성자가_자신이_아닌_경우_테스트() {
        Answers answers = new Answers(List.of(new Answer(NsUserTest.JAVAJIGI, "content"), new Answer(NsUserTest.JAVAJIGI, "content")));

        assertThatThrownBy(() -> {
            answers.throwExceptionIfOwner(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }
}
