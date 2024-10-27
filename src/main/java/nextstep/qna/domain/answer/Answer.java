package nextstep.qna.domain.answer;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.domain.BaseEntity;
import nextstep.qna.domain.DeleteHistory.ContentType;
import nextstep.qna.domain.DeleteHistory.DeleteHistory;
import nextstep.qna.domain.question.Question;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class Answer {

    public static final String DELETE_EXCEPT_MESSAGE = "답변을 삭제할 권한이 없습니다.";

    private final BaseEntity baseEntity;
    private final Comments comments;
    private boolean deleted;

    public Answer(NsUser writer, String contents) {
        this(0L, writer, contents);
    }
    
    public Answer(NsUser writer, String contents, boolean deleted) {
        this(0L, writer, contents, deleted);
    }

    public Answer(Long id, NsUser writer, String contents) {
        this(id, writer, contents, false);
    }

    public Answer(Long id, NsUser writer, String contents, boolean deleted) {
        this(new BaseEntity(id), new Comments(writer, contents), deleted);
    }

    public Answer(BaseEntity baseEntity, Comments comments, boolean deleted) {
        this.baseEntity = baseEntity;
        this.comments = comments;
        this.deleted = deleted;
    }

    public void delete(NsUser loginUser) {
        if (!this.isOwner(loginUser)) {
            throw new CannotDeleteException(DELETE_EXCEPT_MESSAGE);
        }
        this.deleted = true;
    }

    public DeleteHistory toDeleteHistory() {
        return new DeleteHistory(ContentType.ANSWER, this.baseEntity.getId(), this.comments.getWriter(), LocalDateTime.now());
    }

    private boolean isOwner(NsUser loginUser) {
        return this.comments.isOwner(loginUser);
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
