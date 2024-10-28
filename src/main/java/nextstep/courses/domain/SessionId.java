package nextstep.courses.domain;

import java.util.Objects;

public class SessionId {
    private Long id;
    private String name;

    public SessionId(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SessionId of(Long id, String name) {
        return new SessionId(id, name);
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
        return Objects.equals(id, sessionId.id) && Objects.equals(name, sessionId.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
