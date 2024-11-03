package nextstep.courses.domain.enroll;

import java.time.LocalDateTime;
import java.util.Objects;

public class EnrollUserInfo {
    private final Long sessionId;
    private final Long nsUserId;

    public EnrollUserInfo(Long sessionId, Long nsUserId) {
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EnrollUserInfo that = (EnrollUserInfo) o;
        return Objects.equals(sessionId, that.sessionId) && Objects.equals(nsUserId, that.nsUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, nsUserId);
    }

}
