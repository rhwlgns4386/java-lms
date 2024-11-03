package nextstep.courses.domain;

import java.util.Objects;

public class SessionImage {
    private final Long id;
    private final Long sessionId;
    private final Long imageId;

    public SessionImage(Long sessionId, long imageId) {
        this(0L, sessionId, imageId);
    }

    public SessionImage(Long id, long sessionId, long imageId) {
        this.id = id;
        this.sessionId = sessionId;
        this.imageId = imageId;
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getImageId() {
        return imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SessionImage))
            return false;
        SessionImage that = (SessionImage)o;
        return Objects.equals(id, that.id) && Objects.equals(sessionId, that.sessionId) && Objects.equals(imageId,
            that.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sessionId, imageId);
    }
}
