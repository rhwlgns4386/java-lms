package nextstep.qna.service;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.NotFoundException;
import nextstep.qna.domain.*;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service("qnaService")
public class QnAService {
    @Resource(name = "questionRepository")
    private QuestionRepository questionRepository;

    @Resource(name = "answerRepository")
    private AnswerRepository answerRepository;

    @Resource(name = "deleteHistoryService")
    private DeleteHistoryService deleteHistoryService;

/*
    @Transactional
    public void deleteQuestionASIS(NsUser loginUser, long questionId) throws CannotDeleteException {
        Question question = questionRepository.findById(questionId).orElseThrow(NotFoundException::new);
        if (!question.isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }

        List<Answer> answers = question.getAnswers();
        for (Answer answer : answers) {
            if (!answer.isOwner(loginUser)) {
                throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
            }
        }

        List<DeleteHistory> deleteHistories = new ArrayList<>();
        question.setDeleted(true);
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION, questionId, question.getWriter(), LocalDateTime.now()));
        for (Answer answer : answers) {
            answer.setDeleted(true);
            deleteHistories.add(new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), LocalDateTime.now()));
        }
        deleteHistoryService.saveAll(deleteHistories);
    }
*/
    @Transactional
    public void deleteQuestion(NsUser loginUser, long questionId) throws CannotDeleteException {
        Question question = getQuestion(questionId);
        DeleteHistorys deleteHistorys = question.detleteQuestionAndAnswer(loginUser);
        saveDeleteHistory(deleteHistorys);
    }

    private void saveDeleteHistory2(long questionId, Question question, Answers answers) {
        DeleteHistorys deleteHistories = new DeleteHistorys(new ArrayList<>());
        deleteHistories.addDeleteHistorys(question, questionId, answers);
        deleteHistoryService.saveAll(deleteHistories.getDeleteHistories());
    }

    private void saveDeleteHistory(DeleteHistorys deleteHistories) {
        //DeleteHistorys deleteHistories = new DeleteHistorys(new ArrayList<>());
        //deleteHistories.addDeleteHistorys(question, questionId, answers);
        deleteHistoryService.saveAll(deleteHistories.getDeleteHistories());
    }

    private Question getQuestion(long questionId) {
        return questionRepository.findById(questionId).orElseThrow(NotFoundException::new);
    }
}
