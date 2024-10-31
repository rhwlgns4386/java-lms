package nextstep.courses.domain.session;

import nextstep.qna.SessionException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class SessionStudents {

    private Long id;
    private int maximumNumberPeople;
    private List<NsUser> students = new ArrayList<>();

    public SessionStudents(int maximumNumberPeople) {
        this.maximumNumberPeople = maximumNumberPeople;
    }

    public void registration(NsUser nsUser) {
        validate(nsUser);
        this.students.add(nsUser);
    }

    public boolean isExceeds() {
        return this.students.size() == maximumNumberPeople;
    }

    private void validate(NsUser nsUser) {
        duplicate(nsUser);
    }

    private void duplicate(NsUser nsUser) {
        if (this.students.contains(nsUser)) {
            throw new SessionException("이미 수강신청을 했습니다.");
        }
    }


}
