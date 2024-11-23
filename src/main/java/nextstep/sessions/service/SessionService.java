package nextstep.sessions.service;

import nextstep.qna.NotFoundException;
import nextstep.sessions.domain.SessionRepository;
import nextstep.studentsessions.domain.StudentSession;
import nextstep.studentsessions.domain.StudentSessionRepository;

import javax.annotation.Resource;

public abstract class SessionService {
    @Resource(name = "sessionRepository")
    protected SessionRepository sessionRepository;
    @Resource(name = "studentSessionRepository")
    private StudentSessionRepository studentSessionRepository;

    public void rejectSessionRegistration(Long sessionId, Long studentId) {
        StudentSession studentSession = getStudentSession(sessionId, studentId);

        studentSession.reject();
    }
    public void approveSessionRegistration(Long sessionId, Long studentId) {
        StudentSession studentSession = getStudentSession(sessionId, studentId);

        studentSession.approve();
    }

    private StudentSession getStudentSession(Long sessionId, Long studentId) {
        return studentSessionRepository.findBySessionIdAndStudentId(sessionId, studentId)
                .orElseThrow(NotFoundException::new);
    }
}
