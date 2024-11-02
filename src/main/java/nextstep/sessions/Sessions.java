package nextstep.sessions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sessions {
    private final List<Session> sessions;

    public Sessions(List<Session> sessions) {
        this.sessions = new ArrayList<>(sessions);
    }

    public Sessions() {
        this.sessions = new ArrayList<>();
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public List<Session> getSessions() {
        return Collections.unmodifiableList(sessions);
    }

    public boolean contains(Session session) {
        return sessions.contains(session);
    }

    public void validateSession(Session session) {
        if (!sessions.contains(session)) {
            throw new IllegalStateException("해당 코스에 해당하는 강의가 아닙니다.");
        }
    }

    public List<Session> clone() {
        return new ArrayList<>(sessions);
    }

    public int size() {
        return sessions.size();
    }
}
