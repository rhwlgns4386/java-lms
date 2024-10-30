package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteHistoriesTest {

    @Test
    void 삭제이력_생성() {
        Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers contents1");
        DeleteHistory deleteHistory1 = new DeleteHistory(ContentType.QUESTION, Q1.getId(), Q1.getWriter(), LocalDateTime.now());
        DeleteHistory deleteHistory2 = new DeleteHistory(ContentType.ANSWER, A1.getId(), A1.getWriter(), LocalDateTime.now());
        DeleteHistories deleteHistories = new DeleteHistories(Arrays.asList(deleteHistory1, deleteHistory2));

        assertThat(deleteHistories).isEqualTo(new DeleteHistories(Arrays.asList(
                new DeleteHistory(ContentType.QUESTION, Q1.getId(), Q1.getWriter(), LocalDateTime.now()),
                new DeleteHistory(ContentType.ANSWER, A1.getId(), A1.getWriter(), LocalDateTime.now())
        )));
    }

//    @Test
//    void 삭제이력_추가() {
//        Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
//        Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers contents1");
//        Answer A2 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers contents2");
//        DeleteHistory deleteHistory1 = new DeleteHistory(ContentType.QUESTION, Q1.getId(), Q1.getWriter(), LocalDateTime.now());
//        DeleteHistory deleteHistory2 = new DeleteHistory(ContentType.ANSWER, A1.getId(), A1.getWriter(), LocalDateTime.now());
//        DeleteHistories deleteHistories = new DeleteHistories(Arrays.asList(deleteHistory1, deleteHistory2));
//        deleteHistories.addDeleteHistory(new DeleteHistory(ContentType.ANSWER, A2.getId(), A2.getWriter(), LocalDateTime.now()));
//
//        assertThat(deleteHistories).isEqualTo(new DeleteHistories(Arrays.asList(
//                new DeleteHistory(ContentType.QUESTION, Q1.getId(), Q1.getWriter(), LocalDateTime.now()),
//                new DeleteHistory(ContentType.ANSWER, A1.getId(), A1.getWriter(), LocalDateTime.now()),
//                new DeleteHistory(ContentType.ANSWER, A2.getId(), A2.getWriter(), LocalDateTime.now())
//        )));
//    }
}