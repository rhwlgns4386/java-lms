package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Answers {
    private final List<Answer> answers;

    public Answers() {
        this(new ArrayList<>());
    }

    public Answers(List<Answer> answers) {
        this.answers = new ArrayList<>(answers);
    }

    public void deleteAnswers(NsUser user) throws CannotDeleteException {
        for (Answer answer : answers) {
            answer.delete(user);
        }
    }

    public List<DeleteHistory> ofDeleteHistories() {
        return answers.stream()
                .map(Answer::ofDeleteHistory)
                .collect(Collectors.toList());
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answers answers1 = (Answers) o;
        return Objects.equals(answers, answers1.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answers);
    }
}
