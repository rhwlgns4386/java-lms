package nextstep.qna.domain;

import nextstep.global.domain.BaseEntity;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Question extends BaseEntity {
    private String title;

    private String contents;

    private NsUser writer;

    private Answers answers;

    private boolean deleted = false;

    public Question(NsUser writer, String title, String contents, LocalDateTime createdAt) {
        this(0L, writer, title, contents, createdAt);
    }

    public Question(Long id, NsUser writer, String title, String contents, LocalDateTime createdAt) {
        super(id, createdAt);
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.answers = new Answers();
    }

    public Long getId() {
        return id;
    }

    public NsUser getWriter() {
        return writer;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }

    public List<DeleteHistory> delete(NsUser loginUser, LocalDateTime deletedAt) throws CannotDeleteException {
        this.validateWriter(loginUser);
        this.deleted = true;
        this.updatedAt = deletedAt;

        List<DeleteHistory> deleteHistories = new ArrayList<>();

        deleteHistories.add(this.toDeleteHistory(deletedAt));
        if (this.hasAnswers()) {
            deleteHistories.addAll(this.answers.delete(loginUser, deletedAt));
        }

        return deleteHistories;
    }

    private void validateWriter(NsUser loginUser) throws CannotDeleteException {
        if (loginUser.isSameUser(this.writer)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    private boolean hasAnswers() {
        return !this.answers.isEmpty();
    }

    private DeleteHistory toDeleteHistory(LocalDateTime deletedAt) {
        return new DeleteHistory(ContentType.QUESTION, this.id, this.writer, deletedAt);
    }
}
