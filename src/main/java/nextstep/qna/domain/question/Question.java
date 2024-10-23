package nextstep.qna.domain.question;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.BaseEntity;
import nextstep.qna.domain.answer.Comments;
import nextstep.qna.domain.answer.Answer;
import nextstep.qna.domain.answer.Answers;
import nextstep.users.domain.NsUser;

import java.util.List;
import java.util.Objects;

public class Question {

    private final BaseEntity baseEntity;
    private final QuestionContents questionComments;
    private boolean deleted = false;

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(NsUser writer, String title, String contents, boolean deleted) {
        this(0L, writer, title, contents, deleted);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this(new BaseEntity(id), new QuestionContents(title, new Comments(writer, contents), new Answers()), false);
    }

    public Question(Long id, NsUser writer, String title, String contents, boolean deleted) {
        this(new BaseEntity(id), new QuestionContents(title, new Comments(writer, contents), new Answers()), deleted);
    }

    public Question(BaseEntity baseEntity, QuestionContents questionComments, boolean deleted) {
        this.baseEntity = baseEntity;
        this.questionComments = questionComments;
        this.deleted = deleted;
    }

    public Long getId() {
        return this.baseEntity.getId();
    }

    public NsUser getWriter() {
        return this.questionComments.getWriter();
    }

    public void addAnswer(Answer answer) {
        questionComments.addAnswer(answer);
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
        return questionComments.getAnswers();
    }

    public void throwExceptionIfAnswerIsOwner(NsUser loginUser) {
        this.questionComments.throwExceptionIfAnswerIsOwner(loginUser);
    }

    public void throwExceptionIfOwner(NsUser loginUser) {
        if (!this.isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return isDeleted() == question.isDeleted() && Objects.equals(baseEntity, question.baseEntity) && Objects.equals(questionComments, question.questionComments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseEntity, questionComments, isDeleted());
    }

    public Question delete() {
        this.deleted = true;
        this.questionComments.deleteAnswer();
        return this;
    }
}
