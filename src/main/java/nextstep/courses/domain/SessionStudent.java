package nextstep.courses.domain;

public class SessionStudent {
    private final Long id;

    private final Long sessionId;

    private final Long nsUserId;

    private boolean isSelected;

    public SessionStudent(Long sessionId, Long nsUserId) {
        this(null, sessionId, nsUserId, false);
    }

    public SessionStudent(Long sessionId, Long nsUserId, boolean isSelected) {
        this(null, sessionId, nsUserId, isSelected);
    }

    public SessionStudent(Long id, Long sessionId, Long nsUserId, boolean isSelected) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.isSelected = isSelected;
    }

    public void updateSelected() {
        isSelected = true;
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isNotSelected() {
        return !isSelected();
    }
}
