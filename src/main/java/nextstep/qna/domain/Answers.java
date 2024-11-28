package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

public class Answers {
    private List<Answer> answers;

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public Answers(Answer... answers) {
        this(new ArrayList<>(Arrays.asList(answers)));
    }

    public Answers() {
        this(new ArrayList<>());
    }

    public List<DeleteHistory> delete(NsUser loginUser) throws CannotDeleteException {
        ArrayList<DeleteHistory> result = new ArrayList<>();
        for (Answer answer : answers) {
            result.add(answer.delete(loginUser));
        }
        return result;
    }

    public void add(Answer answer) {
        this.answers.add(answer);
    }
}
