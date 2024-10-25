package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Question extends Auditable {
    private Long id;

    private QuestionBody questionBody;

    private NsUser writer;

    private Answers answers = new Answers();

    private boolean deleted = false;

    public Question() {
    }

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, new QuestionBody(title, contents));
    }

    public Question(Long id, NsUser writer, QuestionBody questionBody) {
        this.id = id;
        this.writer = writer;
        this.questionBody = questionBody;
    }

    public Long getId() {
        return id;
    }

    public NsUser getWriter() {
        return writer;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.addAnswer(answer);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete(NsUser user) throws CannotDeleteException {
        if (!isOwner(user)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }

        this.deleted = true;
        answers.deleteAnswers(user);
    }

    public boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }

    public List<DeleteHistory> ofDeleteHistories() {
        List<DeleteHistory> result = new ArrayList<>();
        result.add(this.ofDeleteHistory());
        result.addAll(answers.ofDeleteHistories());
        return result;
    }

    public DeleteHistory ofDeleteHistory() {
        return DeleteHistory.ofQuestion(id, writer, LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionBody=" + questionBody +
                ", writer=" + writer +
                ", answers=" + answers +
                ", deleted=" + deleted +
                '}';
    }
}
