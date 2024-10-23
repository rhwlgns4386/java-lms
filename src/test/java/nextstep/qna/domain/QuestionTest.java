package nextstep.qna.domain;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    void create() {
        BaseEntity baseEntity = new BaseEntity(1L, LocalDateTime.now(), LocalDateTime.now());
        QuestionComments questionComments = new QuestionComments("title", "comments", new NsUser());
        Answers answers = new Answers(List.of());
        assertThat(new Question(baseEntity, questionComments, answers)).isEqualTo(new Question(baseEntity, questionComments, answers));
    }
}
