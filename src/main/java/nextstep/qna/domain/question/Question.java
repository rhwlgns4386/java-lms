package nextstep.qna.domain.question;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.BaseEntity;
import nextstep.qna.domain.DeleteHistory.ContentType;
import nextstep.qna.domain.DeleteHistory.DeleteHistory;
import nextstep.qna.domain.answer.Comments;
import nextstep.qna.domain.answer.Answer;
import nextstep.qna.domain.answer.Answers;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question {

    private final BaseEntity baseEntity;
    private final QuestionContents questionContents;
    private boolean deleted;

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

    public Question(Long id, NsUser writer, String title, String contents, boolean deleted, List<Answer> answers) {
        this(new BaseEntity(id), new QuestionContents(title, new Comments(writer, contents), new Answers(answers)), deleted);
    }

    public Question(BaseEntity baseEntity, QuestionContents questionComments, boolean deleted) {
        this.baseEntity = baseEntity;
        this.questionContents = questionComments;
        this.deleted = deleted;
    }

    public void addAnswer(Answer answer) {
        questionContents.addAnswer(answer);
    }

    private boolean isOwner(NsUser loginUser) {
        return this.questionContents.isOwner(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete(NsUser loginUser) {
        if (!this.isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
        this.deleted = true;
        this.questionContents.deleteAnswer(loginUser);
    }

    public DeleteHistory toDeleteHistory() {
        return new DeleteHistory(ContentType.QUESTION, this.baseEntity.getId(), this.questionContents.getWriter(), LocalDateTime.now());
    }

    public List<DeleteHistory> toDeleteHistories() {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(toDeleteHistory());
        deleteHistories.addAll(this.questionContents.toDeleteHistories());
        return deleteHistories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return isDeleted() == question.isDeleted() && Objects.equals(baseEntity, question.baseEntity) && Objects.equals(questionContents, question.questionContents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseEntity, questionContents, isDeleted());
    }
}
