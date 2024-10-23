package nextstep.qna.domain.answer;

import nextstep.qna.domain.BaseEntity;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {

    @Test
    void create() {
        assertThat(new Answer(NsUserTest.JAVAJIGI, "contents")).isEqualTo(new Answer(NsUserTest.JAVAJIGI, "contents"));
    }
}
