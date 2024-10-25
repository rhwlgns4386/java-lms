package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");


    @Test
    public void createDeleteHsitory테스트() {
        assertThat(Q1.createDeleteHistory()).isNotNull();
    }

    @Test
    public void DeleteHsitories테스트() {
        assertThat(Q1.deleteHistories()).isNotNull();
    }

}
