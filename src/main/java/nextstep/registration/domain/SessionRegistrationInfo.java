package nextstep.registration.domain;

import nextstep.sessions.domain.Session;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class SessionRegistrationInfo {

    private Session session;

    private NsUser user;

    private RegistrationStatus registrationStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public SessionRegistrationInfo(Session session, NsUser user) {
        this(session, user, RegistrationStatus.PENDING, null, null);
    }

    public SessionRegistrationInfo(NsUser user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(null, user, RegistrationStatus.PENDING, createdAt, updatedAt);
    }

    public SessionRegistrationInfo(NsUser user, RegistrationStatus registrationStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(null, user, registrationStatus, createdAt, updatedAt);
    }

    public SessionRegistrationInfo(Session session, NsUser user, RegistrationStatus registrationStatus) {
        this(session, user, registrationStatus, null, null);
    }

    public SessionRegistrationInfo(Session session, NsUser user, RegistrationStatus registrationStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.session = session;
        this.user = user;
        this.registrationStatus = registrationStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public NsUser getUser() {
        return user;
    }

    public Long getSessionId() {
        return session.getId();
    }

    public Session getSession() {
        return session;
    }

    public Long getUserId() {
        return user.getId();
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void approve() {
        this.registrationStatus = RegistrationStatus.APPROVED;
    }

    public void reject() {
        this.registrationStatus = RegistrationStatus.REJECTED;
    }
}
