package nextstep.courses.domain.enroll;

import nextstep.courses.domain.session.SessionPriceType;
import nextstep.courses.domain.session.SessionStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class EnrollUserInfo {
    private final Long sessionId;
    private final Long nsUserId;

    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    public EnrollUserInfo(Long sessionId, Long nsUserId) {
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.updatedAt = LocalDateTime.now();
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
