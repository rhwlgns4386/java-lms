package nextstep.qna.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswersTest {

    @Test
    void create() {

        assertThat(new Answers()).isEqualTo(new Answers());
    }
}
