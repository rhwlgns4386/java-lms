package nextstep.courses.domain;

import java.util.Objects;
import nextstep.users.domain.NsUser;

public class EnrollmentStudent {
    private long sessionId;

    private long userId;

    private RequestStatus requestStatus;

    public EnrollmentStudent(Session session, NsUser user) {
        this(session.id(), user.getId());
    }

    public EnrollmentStudent(Session session, NsUser user, RequestStatus requestStatus) {
        this(session.id(), user.getId(), requestStatus);
    }

    public EnrollmentStudent(long sessionId, long userId) {
        this(sessionId, userId, RequestStatus.PENDING);
    }

    public EnrollmentStudent(long sessionId, long userId, RequestStatus requestStatus) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.requestStatus = requestStatus;
    }

    public void accept() {
        this.requestStatus = RequestStatus.ACCEPTED;
    }

    public void reject() {
        this.requestStatus = RequestStatus.REJECT;
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

    public String getRequestStatusString() {
        return requestStatus.name();
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
        return sessionId == that.sessionId && userId == that.userId && requestStatus == that.requestStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId, requestStatus);
    }
}
