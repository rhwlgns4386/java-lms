package nextstep.qna.domain;

import static nextstep.qna.domain.DeleteHistoryFactory.createDeleteHistory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

class DeleteRuleTest {

    @Test
    void 작성자이면_삭제() throws CannotDeleteException {
        DeleteRule deleteRule = new DeleteRule(NsUserTest.JAVAJIGI, "TestMessage");

        DeleteHistory history = deleteRule.delete(NsUserTest.JAVAJIGI, ContentType.QUESTION, 0L);

        assertThat(deleteRule.isDeleted()).isTrue();
        assertThat(history).isEqualTo(createDeleteHistory(ContentType.QUESTION, 0L, NsUserTest.JAVAJIGI));
    }

    @Test
    void 작성자가아니면_예외() {
        DeleteRule deleteRule = new DeleteRule(NsUserTest.JAVAJIGI, "TestMessage");

        assertThatThrownBy(() -> deleteRule.delete(NsUserTest.SANJIGI, ContentType.QUESTION, 0L)).isInstanceOf(
                CannotDeleteException.class);
    }
}
