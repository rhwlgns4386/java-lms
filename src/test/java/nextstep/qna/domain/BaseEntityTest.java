package nextstep.qna.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseEntityTest {

    @Test
    void create() {
        LocalDateTime now = LocalDateTime.now();
        assertThat(new BaseEntity(1L, now, now)).isEqualTo(new BaseEntity(1L, now, now));
    }
}
