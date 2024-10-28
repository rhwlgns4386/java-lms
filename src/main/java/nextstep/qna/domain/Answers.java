package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Answers {
    private final List<Answer> answers;

    public Answers() {
        this.answers = new ArrayList<>();
    }

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public void add(Answer answer) {
        answers.add(answer);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void deleteAll(NsUser user) throws CannotDeleteException {
        validateOnwer(user);

        for (Answer answer : answers) {
            answer.setDeleted(true);
        }
    }

    public List<DeleteHistory> toDeleteHistories() {
        List<DeleteHistory> deleteHistories = new ArrayList<>();

        LocalDateTime createDate = LocalDateTime.now();
        answers.forEach(answer -> deleteHistories.add(answer.toDeleteHistory(createDate)));

        return deleteHistories;
    }

    private void validateOnwer(NsUser user) throws CannotDeleteException {
        if (answers == null || answers.isEmpty()) {
            return;
        }

        if (answers.stream().anyMatch(answer -> answer.isNotOwner(user))) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }
}
