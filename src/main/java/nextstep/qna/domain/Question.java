package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;

public class Question extends QuestionMetaData {
    private Long id;

    private QuestionBoard questionBoard;

    private NsUser writer;

    private Answers answers = new Answers();

    private boolean deleted = false;

    public Question() {
    }

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.questionBoard = new QuestionBoard(title, contents);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return questionBoard.getTitle();
    }

    public String getContents() {
        return questionBoard.getContents();
    }

    public Question setQuestionBoard(String title, String contents) {
        this.questionBoard = new QuestionBoard(title, contents);
        return this;
    }

    public NsUser getWriter() {
        return writer;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Answers getAnswers() {
        return answers;
    }

    public DeleteHistorys deleteQuestion(NsUser loginUser) throws CannotDeleteException {
        validateOwnerCheck(loginUser);
        this.deleted = true;

        DeleteHistorys deleteHistories = new DeleteHistorys(new ArrayList<>());
        deleteHistories.addDeleteQuestionHistory(this, getId());
        return deleteHistories;
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + questionBoard.getTitle() + ", contents=" + questionBoard.getContents() + ", writer=" + writer + "]";
    }

    public void validateOwnerCheck(NsUser loginUser) throws CannotDeleteException {
        if (!this.isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    public DeleteHistorys detleteQuestionAndAnswer(NsUser loginUser) throws CannotDeleteException {
        DeleteHistorys questionHistorys = deleteQuestion(loginUser);
        DeleteHistorys answerHistorys = answers.deleteAnswers(answers.getAnswers(), loginUser);
        return new DeleteHistorys(questionHistorys.getDeleteHistories(), answerHistorys.getDeleteHistories());
    }
}
