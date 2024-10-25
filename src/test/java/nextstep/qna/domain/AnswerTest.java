package nextstep.qna.domain;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");



/*    @Test
    public void 질문자와답변글의_작성자가 같은지테스트() {
        Answer answer = new Answer(new NsUser(),new Question(), "test" );
        assertThat(answer.)
    }*/

}
