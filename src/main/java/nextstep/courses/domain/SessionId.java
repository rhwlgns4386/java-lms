package nextstep.courses.domain;

import java.util.Objects;

public class SessionId {
    private final Long id;

    public SessionId(Long id) {
        this.id = id;
    }

    public long toLonge() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionId sessionId = (SessionId) o;
        return Objects.equals(id, sessionId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
