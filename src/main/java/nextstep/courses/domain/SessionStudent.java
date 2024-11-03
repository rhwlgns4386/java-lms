package nextstep.courses.domain;

import java.util.Objects;

public class SessionStudent {
    private final long id;
    private final long sessionId;
    private final long studentId;
    private SessionStudentStatus sessionStudentStatus;

    public SessionStudent(long sessionId, long studentId) {
        this(0L, sessionId, studentId, SessionStudentStatus.PASS);
    }

    public SessionStudent(long sessionId, long studentId, SessionStudentStatus sessionStudentStatus) {
        this(0L, sessionId, studentId, sessionStudentStatus);
    }

    public SessionStudent(long id, long sessionId, long studentId, SessionStudentStatus sessionStudentStatus) {
        this.id = id;
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.sessionStudentStatus = sessionStudentStatus;
    }

    public long getSessionId() {
        return sessionId;
    }

    public long getStudentId() {
        return studentId;
    }

    public SessionStudentStatus getSessionStudentStatus() {
        return sessionStudentStatus;
    }

    public boolean checkPass() {
        return SessionStudentStatus.checkPass(sessionStudentStatus);
    }

    public boolean checkFail() {
        return SessionStudentStatus.checkFail(sessionStudentStatus);
    }

    public void toRegistered() {
        this.sessionStudentStatus = SessionStudentStatus.REGISTERED;
    }

    public void toCanceled() {
        this.sessionStudentStatus = SessionStudentStatus.CANCELLED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SessionStudent))
            return false;
        SessionStudent that = (SessionStudent)o;
        return id == that.id && getSessionId() == that.getSessionId() && getStudentId() == that.getStudentId()
            && sessionStudentStatus == that.sessionStudentStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getSessionId(), getStudentId(), sessionStudentStatus);
    }
}
