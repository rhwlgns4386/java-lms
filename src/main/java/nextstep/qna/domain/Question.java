package nextstep.qna.domain;

import java.util.ArrayList;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

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
        this(id,new DeleteRule(writer, "질문을 삭제할 권한이 없습니다."),new QuestionBody(title, contents));
    }

    private Question(Long id,DeleteRule deleteRule,QuestionBody questionBody){
        super(id);
        this.deleteRule=deleteRule;
        this.questionBody=questionBody;
    }

    public String getTitle() {
        return questionBody.getTitle();
    }

    public Question setTitle(String title) {
        this.questionBody.updateTitle(title);
        return this;
    }

    public String getContents() {
        return questionBody.getContents();
    }

    public Question setContents(String contents) {
        this.questionBody.updateContents(contents);
        return this;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public ArrayList<DeleteHistory> delete(NsUser loginUser) throws CannotDeleteException {
        deleteRule.delete(loginUser);

        ArrayList<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION, getId(), getWriter(), LocalDateTime.now()));
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
