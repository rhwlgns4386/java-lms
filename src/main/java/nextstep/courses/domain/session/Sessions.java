package nextstep.courses.domain.session;

import java.util.List;

public class Sessions {

    private List<Session> sessions;

    public Sessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public void addSession(Session session) {
        this.sessions.add(session);
    }

}
