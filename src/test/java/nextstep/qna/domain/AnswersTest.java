package nextstep.qna.domain;

import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.as;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

class AnswersTest {

    @DisplayName("여러 답글들을 Answers 객체로 생성한다.")
    @Test
    void create() {
        NsUser writer = new NsUser(1L, "moon", "1234", "moonyoonji", "moon@a.com");
        Question question = new Question(1L, writer, "title1", "contents1", LocalDateTime.of(2024, 10, 28, 9, 0, 0));
        Answer answer1 = new Answer(1L, writer, question, "Answers Contents1", LocalDateTime.now());
        Answer answer2 = new Answer(2L, writer, question, "Answers Contents2", LocalDateTime.now());

        Answers actual = new Answers(List.of(answer1, answer2));

        assertThat(actual).extracting("answers", as(LIST))
                .containsExactly(answer1, answer2);
    }
}
