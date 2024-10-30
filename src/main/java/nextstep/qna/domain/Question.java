package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.List;

public class Question {
    private Long id;

    private String title;

    private String contents;

    private NsUser writer;

    private Answers answers;

    private boolean deleted = false;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate;

    public Question() {
    }

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Question setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public Question setContents(String contents) {
        this.contents = contents;
        return this;
    }

    public NsUser getWriter() {
        return writer;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        if (!hasAnswers()) {
            answers = new Answers(List.of(answer));
            return;
        }
        answers.add(answer);
    }

    public boolean hasAnswers() {
        return answers != null;
    }

    public boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }


    public boolean isDeleted() {
        return deleted;
    }

    public DeleteHistories delete(NsUser loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
        deleted = true;

        if (!hasAnswers()) {
            answers = new Answers();
        }

        return createDeleteHistories(loginUser);
    }

    private DeleteHistories createDeleteHistories(NsUser loginUser) throws CannotDeleteException {
        DeleteHistories deleteHistories = answers.deleteAll(loginUser);
        deleteHistories.addDeleteHistory(createDeleteHistory());
        return deleteHistories;
    }

    private DeleteHistory createDeleteHistory() {
        if (!isDeleted()) {
            throw new IllegalStateException("질문이 삭제되지 않아 삭제 이력을 생성할 수 없습니다.");
        }
        return new DeleteHistory(ContentType.QUESTION, getId(), getWriter(), LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }
}
