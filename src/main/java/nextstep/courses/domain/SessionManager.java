package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private List<Session> sessions;

    public SessionManager() {
        this(new ArrayList<>());
    }

    public SessionManager(List<Session> sessions) {
        this.sessions = sessions;
    }

    public void addSessions(Session... sessions) {
        this.sessions.addAll(List.of(sessions));
    }

    public List<Session> getSessions() {
        return sessions;
    }
}
