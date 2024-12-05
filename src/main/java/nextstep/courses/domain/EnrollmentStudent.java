package nextstep.courses.domain;

import java.util.Objects;
import nextstep.users.domain.NsUser;

public class EnrollmentStudent {
    private ErollmentStudentId id;

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
        this(new ErollmentStudentId(sessionId, userId), requestStatus);
    }

    private EnrollmentStudent(ErollmentStudentId id, RequestStatus requestStatus) {
        this.id = id;
        this.requestStatus = requestStatus;
    }

    public void accept() {
        this.requestStatus = RequestStatus.ACCEPTED;
    }

    public void reject() {
        this.requestStatus = RequestStatus.REJECT;
    }

    public boolean matchesUserId(Long id) {
        return this.id.matchesUserId(id);
    }

    public long getSessionId() {
        return id.getSessionId();
    }

    public long getUserId() {
        return id.getUserId();
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
        return Objects.equals(id, that.id) && requestStatus == that.requestStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requestStatus);
    }
}
