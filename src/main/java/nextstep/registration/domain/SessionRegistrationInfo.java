package nextstep.registration.domain;

import nextstep.sessions.domain.Session;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class SessionRegistrationInfo {

    private Session session;

    private NsUser user;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public SessionRegistrationInfo(Session session, NsUser user) {
        this.session = session;
        this.user = user;
    }

    public SessionRegistrationInfo(NsUser user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public NsUser getUser() {
        return user;
    }

    public Long getSessionId() {
        return session.getId();
    }

    public Long getUserId() {
        return user.getId();
    }
}
