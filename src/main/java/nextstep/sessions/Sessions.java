package nextstep.sessions;

import java.util.ArrayList;
import java.util.List;

public class Sessions {
    private List<Session> sessions;

    public Sessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public Sessions() {
        this.sessions = new ArrayList<>();
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public boolean contains(Session session) {
        return sessions.contains(session);
    }

    public void validateSession(Session session) {
        if (!sessions.contains(session)) {
            throw new IllegalStateException("해당 코스에 해당하는 강의가 아닙니다.");
        }
    }
}
