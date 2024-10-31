package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Answers {
    private final List<Answer> answers = new ArrayList<>();

    public Answers() {
        this(Collections.EMPTY_LIST);
    }

    public Answers(List<Answer> answers) {
        this.answers.addAll(answers);
    }

    public void validateOwnerCheck(NsUser loginUser) throws CannotDeleteException {
        for (Answer answer : answers) {
            isOwnerCheck(loginUser, answer);
        }
    }

    private void isOwnerCheck(NsUser loginUser, Answer answer) throws CannotDeleteException {
        if (!answer.isOwner(loginUser)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public DeleteHistorys deleteAnswers(List<Answer> answers, NsUser loginUser) throws CannotDeleteException {
        DeleteHistorys deleteHistorys = new DeleteHistorys(new ArrayList<>());

        for (Answer answer : answers) {
            validateOwnerCheck(loginUser);
            answer.deleteAnswer();
        }

        deleteHistorys.addDeleteAnswerHistory(answers);
        return deleteHistorys;
    }

    public void add(Answer answer) {
        answers.add(answer);
    }
}
