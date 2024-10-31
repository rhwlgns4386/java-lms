package nextstep.qna.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeleteHistorys {
    private final List<DeleteHistory> deleteHistories = new ArrayList<>();

    public DeleteHistorys(List<DeleteHistory> deleteHistories){
        this.deleteHistories.addAll(deleteHistories);
    }

    public DeleteHistorys(List<DeleteHistory> questionHistorys, List<DeleteHistory> answerHistorys) {
        this.deleteHistories.addAll(questionHistorys);
        this.deleteHistories.addAll(answerHistorys);
    }

    public List<DeleteHistory> getDeleteHistories() {
        return deleteHistories;
    }

    public void addDeleteQuestionHistory(Question question, long questionId) {
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION, questionId, question.getWriter(), LocalDateTime.now()));
    }

    public void addDeleteAnswerHistory(List<Answer> answers) {
        for (Answer answer : answers) {
            deleteHistories.add(new DeleteHistory(ContentType.ANSWER, answer.getId(), answer.getWriter(), LocalDateTime.now()));
        }
    }

    public List<DeleteHistory> addDeleteHistorys(Question question, long questionId, Answers answers) {
        addDeleteQuestionHistory(question, questionId);
        addDeleteAnswerHistory(answers.getAnswers());
        return this.deleteHistories;
    }
}
