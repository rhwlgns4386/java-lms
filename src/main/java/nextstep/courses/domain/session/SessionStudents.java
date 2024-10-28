package nextstep.courses.domain.session;

import nextstep.qna.SessionException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class SessionStudents {

    private Long id;
    private SessionType sessionType;
    private int maximumNumberPeople;
    private List<NsUser> students = new ArrayList<>();

    public SessionStudents(SessionType sessionType, int maximumNumberPeople) {
        this.sessionType = sessionType;
        this.maximumNumberPeople = maximumNumberPeople;
    }

    public void registration(NsUser nsUser) {
        validate(nsUser);
        this.students.add(nsUser);
    }

    private void validate(NsUser nsUser) {
        duplicate(nsUser);
        validateMaximumNumberPeople();
    }

    private void validateMaximumNumberPeople() {
        if (sessionType == SessionType.PAID && this.students.size() == maximumNumberPeople) {
            throw new SessionException("최대 정원 모집이 끝났습니다");
        }
    }

    private void duplicate(NsUser nsUser) {
        if (this.students.contains(nsUser)) {
            throw new SessionException("이미 수강신청을 했습니다.");
        }
    }

}
