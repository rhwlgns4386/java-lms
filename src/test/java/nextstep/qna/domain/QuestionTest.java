package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    public static final Question Q1 = new Question(JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");


    @Test
    public void createDeleteHsitory테스트() {
        assertThat(Q1.createDeleteHistory(LocalDateTime.now())).isNotNull();
    }

    @Test
    public void DeleteHsitories테스트() {
        assertThat(Q1.deleteHistories(LocalDateTime.now())).isNotNull();
    }

    @Test
    public void Delete테스트() throws CannotDeleteException {
        LocalDateTime currentTime = LocalDateTime.now();
        assertThat(Q1.delete(JAVAJIGI, currentTime)).isEqualTo(List.of(Q1.createDeleteHistory(currentTime)));
    }

}
