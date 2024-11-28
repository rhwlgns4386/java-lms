package nextstep.qna.domain;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void delete(NsUser loginUser) throws CannotDeleteException {
        for (Answer answer : answers) {
            answer.delete(loginUser);
        }
    }

    public void add(Answer answer) {
        this.answers.add(answer);
    }

    public List<Answer> toList() {
        return Collections.unmodifiableList(answers);
    }
}
