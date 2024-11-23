package nextstep.users.domain;

import nextstep.studentsessions.domain.StudentSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NsStudent extends NsUser {
    private final List<StudentSession> studentSessions;

    public NsStudent(NsUser nsUser, LocalDateTime createdAt) {
        this(nsUser, new ArrayList<>(), createdAt, null);
    }

    public NsStudent(NsUser nsUser, List<StudentSession> studentSessions, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(nsUser.getId(), nsUser.userId, nsUser.password, nsUser.name, nsUser.email, studentSessions, createdAt, updatedAt);
    }

    public NsStudent(Long id, String userId, String password, String name, String email, List<StudentSession> studentSessions, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, userId, password, name, email, createdAt, updatedAt);
        this.studentSessions = studentSessions;
    }

    public void registerSession(StudentSession studentSession) {
        this.studentSessions.add(studentSession);
    }
}
