package nextstep.qna.domain.answer;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void add(Answer answer) {
        this.answers.add(answer);
    }


    public void throwExceptionIfOwner(NsUser loginUser) {
        this.answers
                .stream()
                .filter(answer -> !answer.isOwner(loginUser))
                .findAny()
                .ifPresent((answer) -> {throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");});
    }

    public void deleteAnswer() {
        this.answers
                .stream()
                .forEach(o -> o.setDeleted(true));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answers)) return false;
        Answers answers1 = (Answers) o;
        return Objects.equals(getAnswers(), answers1.getAnswers());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAnswers());
    }
}
