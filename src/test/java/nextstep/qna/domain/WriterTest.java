package nextstep.qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

class WriterTest {

    @Test
    void 작성자_검증() {
        Writer writer = new Writer(NsUserTest.JAVAJIGI);
        assertThatIllegalArgumentException().isThrownBy(
                () -> writer.validOwner(NsUserTest.SANJIGI, IllegalArgumentException::new));
    }
}
