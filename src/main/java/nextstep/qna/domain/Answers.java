package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Answers {
    private final List<Answer> answers;

    public Answers() {
        this.answers = new ArrayList<>();
    }

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean isEmpty() {
        return Objects.isNull(this.answers) || this.answers.isEmpty();
    }

    public List<DeleteHistory> delete(NsUser loginUser, LocalDateTime deletedAt) throws CannotDeleteException {
        List<DeleteHistory> deleteHistories = new ArrayList<>();

        for (Answer answer : answers) {
            deleteHistories.add(answer.delete(loginUser, deletedAt));
        }
        return deleteHistories;
    }

    public void add(Answer answer) {
        this.answers.add(answer);
    }
}
