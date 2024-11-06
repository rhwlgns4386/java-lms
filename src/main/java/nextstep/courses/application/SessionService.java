package nextstep.courses.application;

import nextstep.courses.domain.*;
import nextstep.courses.domain.SessionCreate;

public class SessionService {
    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;

    public SessionService(SessionRepository sessionRepository, StudentRepository studentRepository) {
        this.sessionRepository = sessionRepository;
        this.studentRepository = studentRepository;
    }

    public void create(SessionCreate sessionCreate) {
        Session session = Session.from(sessionCreate);
        sessionRepository.save(session);
    }

    public void open(Session session) {
        session.open();
        sessionRepository.save(session);
    }

    public void apply(Long nsUserId, Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));
        Student student = session.apply(new SessionAddInfo(nsUserId, sessionId));
        studentRepository.save(student);
    }
}
