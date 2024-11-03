package nextstep.courses.collection;

import nextstep.courses.domain.Session;

import java.util.ArrayList;
import java.util.List;

public class Sessions {

    private List<Session> sessions = new ArrayList<>();

    public Sessions(List<Session> newSessions) {
        sessions.addAll(newSessions);
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public Session getSessionIdx(int idx) {
        return sessions.get(idx);
    }


}
