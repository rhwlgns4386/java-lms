package nextstep.users.domain;

import java.util.List;
import java.util.stream.Collectors;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionStudent;
import nextstep.courses.domain.Sessions;
import nextstep.courses.domain.Students;

public class NsTeacher {
    private final Long id;
    private final String name;
    private Sessions sessions;

    public NsTeacher(Long id, String name) {
        this(id, name, new Sessions());
    }

    public NsTeacher(String name, Sessions sessions) {
        this(0L, name, sessions);
    }

    public NsTeacher(Long id, String name, Sessions sessions) {
        this.id = id;
        this.name = name;
        this.sessions = sessions;
    }

    public void addSessions(Sessions sessions) {
        this.sessions = sessions;
    }

    public List<Long> findRegisterStudents(long sessionId, List<SessionStudent> sessionStudents) {
        if (isNotOwnSession(sessionId)) {
            throw new IllegalArgumentException("Not matched session teacher");
        }

        return sessionStudents.stream()
            .filter(it -> it.getSessionId() == sessionId)
            .filter(SessionStudent::checkPass)
            .map(SessionStudent::getStudentId)
            .collect(Collectors.toList());
    }

    public List<Long> findCancelStudents(long sessionId, List<SessionStudent> sessionStudents) {
        if (isNotOwnSession(sessionId)) {
            throw new IllegalArgumentException("Not matched session teacher");
        }

        return sessionStudents.stream()
            .filter(it -> it.getSessionId() == sessionId)
            .filter(SessionStudent::checkFail)
            .map(SessionStudent::getStudentId)
            .collect(Collectors.toList());
    }

    public List<SessionStudent> addStudent(long sessionId, Students students, List<SessionStudent> sessionStudents) {
        if (isNotOwnSession(sessionId)) {
            throw new IllegalArgumentException("Not matched session teacher");
        }

        Session session = getSession(sessionId);
        session.addStudents(students);

        return students.toRegistered(sessionStudents);
    }

    public List<SessionStudent> studentCancel(Students students, List<SessionStudent> sessionStudents) {
        return students.toCancel(sessionStudents);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private Session getSession(long sessionId) {
        return sessions.getSession(sessionId);
    }

    private boolean isNotOwnSession(Long sessionId) {
        return !sessions.contains(sessionId);
    }

    public Sessions getSessions() {
        return sessions;
    }
}
