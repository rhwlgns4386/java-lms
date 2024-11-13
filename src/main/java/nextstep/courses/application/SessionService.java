package nextstep.courses.application;

import nextstep.courses.domain.*;
import nextstep.courses.domain.SessionCreate;

import java.util.List;

public class SessionService {
    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;

    public SessionService(SessionRepository sessionRepository, StudentRepository studentRepository) {
        this.sessionRepository = sessionRepository;
        this.studentRepository = studentRepository;
    }

    public void create(SessionCreate sessionCreate, List<SessionImageCreate> images) {
        Session session = Session.from(sessionCreate);
        long sessionId = sessionRepository.save(session);
        for (SessionImageCreate image : images) {
            SessionCoverImage coverImage = SessionCoverImage.from(sessionId, image);
            sessionRepository.save(session);
        }
    }

    public void open(Session session) {
        session.open();
        sessionRepository.save(session);
    }

    public void apply(Long nsUserId, Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));
        List<Student> students = studentRepository.findBySessionId(sessionId);
        Student student = session.sessionApply(nsUserId, students);
        studentRepository.save(student);
    }

    public void updateApprovalStatus(Long studentId, SessionApprovalStatus sessionApprovalStatus) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        student.updateApprovalStatus(sessionApprovalStatus);
        studentRepository.save(student);
    }
}
