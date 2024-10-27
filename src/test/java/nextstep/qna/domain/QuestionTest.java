package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QuestionTest {
    public static final Question Q1 = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
    public static final Question Q2 = new Question(NsUserTest.SANJIGI, "title2", "contents2");

    @Test
    @DisplayName("질문자와 유저가 같으면 질문 삭제 가능")
    void delete_성공() throws Exception {
        Q1.delete(NsUserTest.JAVAJIGI);
        assertThat(Q1.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("질문자와 유저가 다르면 질문 삭제 불가 및 예외 발생")
    void delete_다른_사람이_쓴_글() throws Exception {
        assertThatThrownBy(() -> {
            Q1.delete(NsUserTest.SANJIGI);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    @DisplayName("질문 삭제 시 삭제 이력 생성")
    void toDeleteHistories_생성() throws Exception {
        Q1.addAnswer(new Answer(NsUserTest.JAVAJIGI, Q1, "Answers Contents1"));
        Q1.delete(NsUserTest.JAVAJIGI);
        assertThat(Q1.toDeleteHistories()).hasSize(2);
    }
}
