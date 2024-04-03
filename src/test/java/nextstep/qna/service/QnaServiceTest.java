package nextstep.qna.service;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.answer.Answer;
import nextstep.qna.domain.answer.AnswerRepository;
import nextstep.qna.domain.answer.Answers;
import nextstep.qna.domain.deleteHistory.DeleteHistory;
import nextstep.qna.domain.question.Question;
import nextstep.qna.domain.QuestionTest;
import nextstep.qna.domain.question.QuestionRepository;
import nextstep.qna.domain.deleteHistory.type.ContentType;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QnaServiceTest {
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private DeleteHistoryService deleteHistoryService;

    @InjectMocks
    private QnAService qnAService;

    private Question question;
    private Answers answers;

    @BeforeEach
    public void setUp() throws Exception {
        question = new Question(NsUserTest.JAVAJIGI, "title1", "contents1");
        answers = new Answers();
        answers.add(new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1"));
    }

    @Test
    public void delete_성공() throws Exception {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        when(answerRepository.findByQuestion(question.getId())).thenReturn(answers);

        assertThat(question.isDeleted()).isFalse();
        qnAService.deleteQuestion(NsUserTest.JAVAJIGI, question.getId());

        assertThat(question.isDeleted()).isTrue();
        verifyDeleteHistories();
    }

    @Test
    public void delete_다른_사람이_쓴_글() throws Exception {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));

        assertThatThrownBy(() -> {
            qnAService.deleteQuestion(NsUserTest.SANJIGI, question.getId());
        }).isInstanceOf(CannotDeleteException.class);
    }

    @Test
    public void delete_성공_질문자_답변자_같음() throws Exception {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
        when(answerRepository.findByQuestion(question.getId())).thenReturn(answers);

        qnAService.deleteQuestion(NsUserTest.JAVAJIGI, question.getId());

        assertThat(question.isDeleted()).isTrue();
        assertThat(answers.get(0).isDeleted()).isTrue();
        verifyDeleteHistories();
    }

    @Test
    public void delete_답변_중_다른_사람이_쓴_글() throws Exception {
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));

        assertThatThrownBy(() -> {
            qnAService.deleteQuestion(NsUserTest.SANJIGI, question.getId());
        }).isInstanceOf(CannotDeleteException.class);
    }

    private void verifyDeleteHistories() {
        List<DeleteHistory> deleteHistories = Arrays.asList(
                new DeleteHistory(ContentType.QUESTION, question.getId(), question.getWriter()),
                new DeleteHistory(ContentType.ANSWER, answers.get(0).getId(), answers.get(0).getWriter()));
        verify(deleteHistoryService).saveAll(deleteHistories);
    }
}
