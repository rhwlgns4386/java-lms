package nextstep.qna.domain.answer;

import nextstep.qna.domain.BaseEntity;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {

    @Test
    void create() {
        LocalDateTime now = LocalDateTime.now();
        BaseEntity baseEntity = new BaseEntity(1L, now, now);
        Comments contents = new Comments(new NsUser(), "contents");

        assertThat(new Answer(baseEntity, contents)).isEqualTo(new Answer(baseEntity, contents));
    }
}
