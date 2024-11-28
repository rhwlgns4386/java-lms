package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

class DeleteRuleTest {

    @Test
    void 작성자이면_삭제() throws CannotDeleteException {
        DeleteRule deleteRule = new DeleteRule(NsUserTest.JAVAJIGI, "TestMessage");

        deleteRule.delete(NsUserTest.JAVAJIGI);

        assertThat(deleteRule.isDeleted()).isTrue();
    }

    @Test
    void 작성자가아니면_예외() {
        DeleteRule deleteRule = new DeleteRule(NsUserTest.JAVAJIGI, "TestMessage");

        assertThatThrownBy(()->deleteRule.delete(NsUserTest.SANJIGI)).isInstanceOf(CannotDeleteException.class);
    }
}
