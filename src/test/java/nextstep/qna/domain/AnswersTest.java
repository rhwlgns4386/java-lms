package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;
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
        NsUser loginUser = new NsUser(1L);
        BaseEntity baseEntity = new BaseEntity(1L);
        Answers answers = new Answers(List.of(new Answer(baseEntity, new Comments(loginUser, "title")), new Answer(baseEntity, new Comments(loginUser, "title"))));

        answers.throwExceptionIfOwner(loginUser);
    }

    @Test
    void 작성자가_자신이_아닌_경우_테스트() {
        NsUser loginUser = new NsUser(1L);
        NsUser loginUser2 = new NsUser(2L);
        BaseEntity baseEntity = new BaseEntity(1L);
        Answers answers = new Answers(List.of(new Answer(baseEntity, new Comments(loginUser, "title")), new Answer(baseEntity, new Comments(loginUser, "title"))));

        assertThatThrownBy(() -> {
            answers.throwExceptionIfOwner(loginUser2);
        }).isInstanceOf(CannotDeleteException.class);
    }
}
