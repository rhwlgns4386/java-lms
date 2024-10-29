package nextstep.courses.domain;

import java.util.Objects;

public class SessionId {
    private Long id;
    private String title;

    public SessionId(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static SessionId of(Long id, String name) {
        return new SessionId(id, name);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionId)) {
            return false;
        }
        SessionId sessionId = (SessionId) o;
        return Objects.equals(id, sessionId.id) && Objects.equals(title, sessionId.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
