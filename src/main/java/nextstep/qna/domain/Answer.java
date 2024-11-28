package nextstep.qna.domain;

import java.util.Optional;
import nextstep.qna.CannotDeleteException;
import nextstep.qna.NotFoundException;
import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Answer extends UserBaseEntity {
    private Long id;

    private Question question;

    private String contents;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate;
    private boolean deleted = false;

    public Answer() {
    }

    @Override
    protected CannotDeleteException createException() {
        return new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
    }

    public Answer(NsUser writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

    public Answer(Long id, NsUser writer, Question question, String contents) {
        this(id, Optional.ofNullable(writer), Optional.ofNullable(question), contents);
    }

    private Answer(Long id, Optional<NsUser> writer, Optional<Question> question, String contents) {
        super(writer.orElseThrow(UnAuthorizedException::new));
        this.id = id;
        this.question = question.orElseThrow(NotFoundException::new);
        this.contents = contents;
    }

    public void delete(NsUser loginUser) throws CannotDeleteException {
        validOwner(loginUser);
        this.deleted = true;
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    public Answer setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Answer [id=" + getId() + ", writer=" + getWriter() + ", contents=" + contents + "]";
    }
}
