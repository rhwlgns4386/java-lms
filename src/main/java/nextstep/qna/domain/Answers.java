package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class Answers {

    private final List<Answer> answers;

    public Answers() {
        this.answers = new ArrayList<>();
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public boolean isEmpty() {
        return answers.isEmpty();
    }

    public List<DeleteHistory> delete(NsUser nsUser) throws CannotDeleteException {
        List<DeleteHistory> result = new ArrayList<>();
        for (Answer answer : answers) {
            result.add(answer.delete(nsUser));
        }
        return result;
    }

}
