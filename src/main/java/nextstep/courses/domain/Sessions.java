package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sessions {

    private final List<Session> sessions;

    public Sessions() {
        this(new ArrayList<>());
    }

    public Sessions(List<Session> sessionList) {
        this.sessions = sessionList;
    }

    public int size() {
        return this.sessions.size();
    }

    public Session getSession(long sessionId) {
        return sessions.stream()
            .filter(it -> it.compareId(sessionId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("not found session"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Sessions))
            return false;
        Sessions sessions1 = (Sessions)o;
        return Objects.equals(sessions, sessions1.sessions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sessions);
    }
}
