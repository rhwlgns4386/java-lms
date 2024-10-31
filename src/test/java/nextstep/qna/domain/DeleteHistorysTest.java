package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteHistorysTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    private List<Answer> answers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        answers.add(A1);
        answers.add(A2);
    }

    @Test
    @DisplayName("답변 삭제 히스토리를 답변자 만큼 담는다.")
    void addDeleteAnswerHistory() {
        DeleteHistory history = new DeleteHistory(ContentType.ANSWER, A1.getId(), A1.getWriter(), LocalDateTime.now());
        DeleteHistorys deleteHistorys = new DeleteHistorys(new ArrayList<>());

        deleteHistorys.addDeleteAnswerHistory(answers);

        assertThat(deleteHistorys.getDeleteHistories()).hasSize(2);
    }

    @Test
    @DisplayName("질문 삭제 히스토리를 질문자 만큼 담는다.")
    void addDeleteQuestionHistory() {
        DeleteHistorys deleteHistorys = new DeleteHistorys(new ArrayList<>());
        deleteHistorys.addDeleteQuestionHistory(Q1, 1);
        deleteHistorys.addDeleteQuestionHistory(Q2, 1);

        assertThat(deleteHistorys.getDeleteHistories()).hasSize(2);

    }
}
