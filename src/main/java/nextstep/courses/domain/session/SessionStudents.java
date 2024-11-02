package nextstep.courses.domain.session;

import nextstep.courses.SessionException;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class SessionStudents {

    private Long id;
    private int maximumNumberPeople;
    private List<SessionStudent> students = new ArrayList<>();

    public SessionStudents(int maximumNumberPeople) {
        this.maximumNumberPeople = maximumNumberPeople;
    }

    public SessionStudent registration(Long sessionId, NsUser nsUser) {
        SessionStudent sessionStudent = new SessionStudent(sessionId, nsUser.getId());
        validate(sessionStudent);
        students.add(sessionStudent);
        return sessionStudent;
    }

    public boolean isExceeds() {
        return this.students.size() == maximumNumberPeople;
    }

    private void validate(SessionStudent sessionStudent) {
        duplicate(sessionStudent);
    }

    private void duplicate(SessionStudent sessionStudent) {
        if (this.students.contains(sessionStudent)) {
            throw new SessionException("이미 수강신청을 했습니다.");
        }
    }

    public int getMaximumNumberPeople() {
        return maximumNumberPeople;
    }

    public void mapping(List<SessionStudent> sessionStudents) {
        this.students = sessionStudents;
    }
}
