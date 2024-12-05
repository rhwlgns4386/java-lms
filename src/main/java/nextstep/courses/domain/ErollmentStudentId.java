package nextstep.courses.domain;

import java.util.Objects;

public class ErollmentStudentId {
    long sessionId;
    long userId;

    public ErollmentStudentId(long sessionId, long userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public boolean matchesUserId(Long id) {
        return this.userId == id;
    }

    public long getSessionId() {
        return sessionId;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErollmentStudentId that = (ErollmentStudentId) o;
        return sessionId == that.sessionId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId);
    }
}
