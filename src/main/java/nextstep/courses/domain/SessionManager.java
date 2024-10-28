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

    public Session findBySessionId(SessionId sessionId) {
        return sessions.stream().filter(it -> it.getSessionId().equals(sessionId)).findFirst().orElse(null);
    }

    public void addFreeSession(SessionDate sessionDate, Image image, SessionId sessionId) {
        FreeSession freeSession = new FreeSession(sessionId, sessionDate, image);
        addSessions(freeSession);
    }

    public void addPaidSession(SessionDate sessionDate, Image image, SessionId sessionId, Integer capacity, Long price) {
        PaidSession paidSession = new PaidSession(sessionDate, image, sessionId, capacity, price);
        addSessions(paidSession);
    }
}
