package nextstep.courses.domain.session;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessionRegistration {
    private final Long sessionId;
    private final Long userId;
    private final LocalDateTime registeredAt;
    private final RegistrationState state;

    public SessionRegistration(Long sessionId, Long userId) {
        this(sessionId, userId, LocalDateTime.now(), new RegistrationState());
    }

    public SessionRegistration(Long sessionId, Long userId, LocalDateTime registeredAt, RegistrationState state) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.registeredAt = registeredAt;
        this.state = state;
    }

    public void select() {
        state.select();
    }

    public void reject() {
        state.reject();
    }

    public void approve() {
        state.approve();
    }

    public void cancel() {
        state.cancel();
    }

    public boolean isSelected() {
        return state.isSelected();
    }

    public boolean isPending() {
        return state.isPending();
    }

    public boolean isApproved() {
        return state.isApproved();
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public SessionRegistrationStatus getRegistrationStatus() {
        return state.getRegistrationStatus();
    }

    public StudentSelectionStatus getSelectionStatus() {
        return state.getSelectionStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionRegistration that = (SessionRegistration) o;
        return Objects.equals(sessionId, that.sessionId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, userId);
    }
}
