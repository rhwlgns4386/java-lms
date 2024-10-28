package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
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


    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.answers = new Answers();
    }

    public void delete(NsUser user) throws CannotDeleteException {
        if (isNotOwner(user)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }

        deleted = true;
        answers.deleteAll(user);
    }

    public DeleteHistory toDeleteHistory() {
        return new DeleteHistory(ContentType.QUESTION, this.id, this.writer, LocalDateTime.now());
    }

    public List<DeleteHistory> toDeleteHistories() {
        List<DeleteHistory> deleteHistories = new ArrayList<>();
        deleteHistories.add(toDeleteHistory());
        deleteHistories.addAll(answers.toDeleteHistories());
        return deleteHistories;
    }

    public void addAnswers(List<Answer> answerList) {
        answerList.forEach(this::addAnswer);
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }

    public boolean isNotOwner(NsUser loginUser) {
        return !isOwner(loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Long getId() {
        return id;
    }

    public NsUser getWriter() {
        return writer;
    }

}
