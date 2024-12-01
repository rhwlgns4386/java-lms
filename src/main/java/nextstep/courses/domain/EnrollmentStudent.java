package nextstep.courses.domain;

import java.util.Objects;
import nextstep.users.domain.NsUser;

public class EnrollmentStudent {
    private long sessionId;

    private long userId;

    public EnrollmentStudent(Session session, NsUser user) {
        this(session.getId(), user.getId());
    }

    public EnrollmentStudent(long sessionId, long userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EnrollmentStudent that = (EnrollmentStudent) o;
        return sessionId == that.sessionId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId);
    }
}
