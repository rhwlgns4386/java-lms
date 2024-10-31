package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Question {
    private static final String QUESTION_AUTHORITY_ERROR = "질문을 삭제할 권한이 없습니다.";

    private Long id;

    private String title;

    private String contents;

    private NsUser writer;

    private Answers answers = new Answers();

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

    public NsUser getWriter() {
        return writer;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }


    public List<DeleteHistory> deleted(NsUser loginUser) throws CannotDeleteException {
        validateAuthority(loginUser);
        deleted = true;

        List<DeleteHistory> histories = new ArrayList<>();
        histories.add(new DeleteHistory(ContentType.QUESTION, id, writer, LocalDateTime.now()));
        histories.addAll(this.answers.makeAnswersDelete(loginUser));

        return histories;
    }

    private void validateAuthority(NsUser loginUser) throws CannotDeleteException {
        if (!isOwner(loginUser)) {
            throw new CannotDeleteException(QUESTION_AUTHORITY_ERROR);
        }
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }


}
