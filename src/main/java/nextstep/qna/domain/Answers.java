package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class Answers {
    private final List<Answer> answers;

    public Answers(Answer... answers) {
        this(List.of(answers));
    }

    public Answers() {
        this(new ArrayList<>());
    }

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public final List<DeleteHistory> delete(NsUser loginUser) throws CannotDeleteException {
        ArrayList<DeleteHistory> result = new ArrayList<>();
        for (Answer answer : answers) {
            result.add(answer.delete(loginUser));
        }
        return result;
    }

    public final void add(Answer answer) {
        this.answers.add(answer);
    }
}
