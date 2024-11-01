package nextstep.users.domain;

import nextstep.sessions.domain.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NsStudent extends NsUser {
    private final List<Session> sessions;

    public NsStudent(NsUser nsUser, LocalDateTime createdAt) {
        this(nsUser, new ArrayList<>(), createdAt, null);
    }

    public NsStudent(NsUser nsUser, List<Session> sessions, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(nsUser, createdAt, updatedAt);
        this.sessions = sessions;
    }

    public void registerSession(Session session) {
        this.sessions.add(session);
    }
}
