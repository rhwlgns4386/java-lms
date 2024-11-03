package nextstep.users.domain;

import nextstep.sessions.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private NsUser nsUser;
    private List<Session> sessions = new ArrayList<>();

    public Student(Long id, String userId, String password, String name, String email) {
        this(id, userId, password, name, email, LocalDateTime.now(), LocalDateTime.now());
    }

    public Student(Long id, String userId, String password, String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.nsUser = new NsUser(id, userId, password, name, email, createdAt, updatedAt);
    }

    public void registerSession(Session session) {
        sessions.add(session);
    }

    public int getSessionCount() {
        return sessions.size();
    }

    public Long getId() {
        return nsUser.getId();
    }
}
