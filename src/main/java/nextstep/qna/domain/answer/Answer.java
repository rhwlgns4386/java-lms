package nextstep.qna.domain.answer;

import nextstep.qna.domain.BaseEntity;
import nextstep.qna.domain.question.Question;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class Answer {

    private BaseEntity baseEntity;
    private Comments comments;
    private boolean deleted = false;

    public Answer() {
    }

    public Answer(NsUser writer, String contents) {
        this(new BaseEntity(0L), new Comments(writer, contents));
    }

    public Answer(Long id, NsUser writer, String contents) {
        this(new BaseEntity(id, LocalDateTime.now(), LocalDateTime.now()), new Comments(writer, contents));
    }

    public Answer(BaseEntity baseEntity, Comments comments) {
        this.baseEntity = baseEntity;
        this.comments = comments;
    }

    public Answer setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isOwner(NsUser loginUser) {
        return this.comments.isOwner(loginUser);
    }

    public Long getId() {
        return this.baseEntity.getId();
    }

    public NsUser getWriter() {
        return this.comments.getWriter();
    }

    @Override
    public String toString() {
        return "Answer [id=" + this.baseEntity.getId() + ", writer=" + this.comments.getWriter() + ", contents=" + comments + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        Answer answer = (Answer) o;
        return deleted == answer.deleted && Objects.equals(baseEntity, answer.baseEntity) && Objects.equals(comments, answer.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseEntity, comments, deleted);
    }
}
