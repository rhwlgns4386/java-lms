package nextstep.qna.service;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.*;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QnaServiceTest {
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private DeleteHistoryService deleteHistoryService;

    @InjectMocks
    private QnAService qnAService;

    private Question question;
    private Answer answer;
    private NsUser writer;

    @BeforeEach
    public void setUp() throws Exception {
        writer = new NsUser(1L, "moon", "1234", "moonyoonji", "moon@a.com");
        question = new Question(1L, writer, "title1", "contents1", LocalDateTime.of(2024, 10, 28, 9, 0, 0));
        answer = new Answer(11L, writer, question, "Answers Contents1", LocalDateTime.of(2024, 10, 28, 9, 10, 0));
        question.addAnswer(answer);
    }

    @Test
    void delete_성공() throws Exception {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));

        assertThat(question.isDeleted()).isFalse();
        qnAService.deleteQuestion(writer, question.getId());

        assertThat(question.isDeleted()).isTrue();
        verifyDeleteHistories();
    }

    @Test
    void delete_다른_사람이_쓴_글() throws Exception {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        NsUser nonWriter = new NsUser(2L, "sun", "5678", "sunyoonji", "sun@a.com");

        assertThatThrownBy(() -> {
            qnAService.deleteQuestion(nonWriter, question.getId());
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    void delete_성공_질문자_답변자_같음() throws Exception {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));

        qnAService.deleteQuestion(writer, question.getId());

        assertThat(question.isDeleted()).isTrue();
        assertThat(answer.isDeleted()).isTrue();
        verifyDeleteHistories();
    }

    @Test
    void delete_답변_중_다른_사람이_쓴_글() throws Exception {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        NsUser nonWriter = new NsUser(2L, "sun", "5678", "sunyoonji", "sun@a.com");

        assertThatThrownBy(() -> {
            qnAService.deleteQuestion(nonWriter, question.getId());
        }).isInstanceOf(CannotDeleteException.class);
    }

    private void verifyDeleteHistories() {
        List<DeleteHistory> deleteHistories = Arrays.asList(
                new DeleteHistory(ContentType.QUESTION, question.getId(), question.getWriter(), LocalDateTime.now()),
                new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), LocalDateTime.now()));
        verify(deleteHistoryService).saveAll(deleteHistories);
    }
}
