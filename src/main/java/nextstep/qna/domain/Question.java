package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class Question extends BaseEntity {

    private QuestionBody questionBody;
    private DeleteRule deleteRule;
    private Answers answers = new Answers();

    public Question() {
    }

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this(id, new DeleteRule(writer, "질문을 삭제할 권한이 없습니다."), new QuestionBody(title, contents));
    }

    private Question(Long id, DeleteRule deleteRule, QuestionBody questionBody) {
        super(id);
        this.deleteRule = deleteRule;
        this.questionBody = questionBody;
    }

    public String getTitle() {
        return questionBody.getTitle();
    }

    public void updateTitle(String title) {
        this.questionBody = this.questionBody.updateTitle(title);
    }

    public String getContents() {
        return questionBody.getContents();
    }

    public void updateContents(String contents) {
        this.questionBody = this.questionBody.updateContents(contents);
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public final List<DeleteHistory> delete(NsUser loginUser) throws CannotDeleteException {
        ArrayList<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(deleteRule.delete(loginUser, ContentType.QUESTION, getId()));
        deleteHistories.addAll(answers.delete(loginUser));
        return deleteHistories;
    }

    public NsUser getWriter() {
        return deleteRule.getWriter();
    }

    public boolean isDeleted() {
        return deleteRule.isDeleted();
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + questionBody.getTitle()
                + ", contents=" + questionBody.getContents() + ", writer=" + getWriter()
                + "]";
    }
}
