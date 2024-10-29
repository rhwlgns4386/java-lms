package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class Answers {
    List<Answer> answers;

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public Answers() {
        this.answers = new ArrayList<>();
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public DeleteHistories deleteAll(NsUser nsUser) throws CannotDeleteException {
        DeleteHistories deleteHistories = new DeleteHistories();
        for (Answer answer : answers) {
            deleteHistories.add(answer.delete(nsUser));
        }
        return deleteHistories;
    }
}
