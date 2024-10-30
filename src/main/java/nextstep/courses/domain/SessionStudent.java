package nextstep.courses.domain;

import java.util.Objects;

public class SessionStudent {
    private final long id;
    private final long sessionId;
    private final long studentId;

    public SessionStudent(long sessionId, long studentId) {
        this(0L, sessionId, studentId);
    }

    public SessionStudent(long id, long sessionId, long studentId) {
        this.id = id;
        this.sessionId = sessionId;
        this.studentId = studentId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public long getStudentId() {
        return studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SessionStudent))
            return false;
        SessionStudent that = (SessionStudent)o;
        return id == that.id && sessionId == that.sessionId && studentId == that.studentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sessionId, studentId);
    }
}
