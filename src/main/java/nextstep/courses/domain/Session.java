package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.List;

import static nextstep.courses.domain.SessionStatus.RECRUITING;

public abstract class Session {

    protected final String sessionId;
    protected final SessionDate date;
    protected final SessionImage image;
    protected final SessionStatus status;
    protected List<NsUser> students;

    public Session(String sessionId, SessionDate date, SessionImage image, SessionStatus status, List<NsUser> numOfStudents) {
        this.sessionId = sessionId;
        this.date = date;
        this.image = image;
        this.status = status;
        this.students = numOfStudents;
    }

    public void enroll(NsUser user) {
        if (!RECRUITING.equals(status)) {
            throw new IllegalStateException("강의가 모집 상태가 아닙니다.");
        }
        this.students.add(user);
    }

    public boolean isContainUser(NsUser user) {
        return students.contains(user);
    }
}
