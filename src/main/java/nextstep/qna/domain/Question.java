package nextstep.qna.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {


    private final BaseEntity baseEntity;
    private final QuestionComments questionComments;
    private final Answers answers;
    private boolean deleted = false;


    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this(new BaseEntity(id), new QuestionComments(title, contents, writer), new Answers());
    }

    public Question(BaseEntity baseEntity, QuestionComments questionComments, Answers answers) {
        this.baseEntity = baseEntity;
        this.questionComments = questionComments;
        this.answers = answers;
    }

    public Long getId() {
        return this.baseEntity.getId();
    }

    public NsUser getWriter() {
        return this.questionComments.getWriter();
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public boolean isOwner(NsUser loginUser) {
        return this.questionComments.getWriter().equals(loginUser);
    }

    public Question setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public List<Answer> getAnswers() {
        return this.answers.getAnswers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return deleted == question.deleted && Objects.equals(baseEntity, question.baseEntity) && Objects.equals(questionComments, question.questionComments) && Objects.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseEntity, questionComments, answers, deleted);
    }
}
