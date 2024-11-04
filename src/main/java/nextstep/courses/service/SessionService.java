package nextstep.courses.service;

import nextstep.courses.domain.enrollment.Student;
import nextstep.courses.domain.session.Session;
import nextstep.courses.entity.SessionEntity;
import nextstep.courses.entity.StudentEntity;
import nextstep.courses.infrastructure.enrollment.StudentRepository;
import nextstep.courses.infrastructure.session.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final StudentRepository studentRepository;
    private final UserService userService;

    public SessionService(SessionRepository sessionRepository, StudentRepository studentRepository,
                          UserService userService) {
        this.sessionRepository = sessionRepository;
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    @Transactional
    public void register(Long sessionId, Payment payment) {
        Session foundSession = Optional.ofNullable(sessionRepository.findById(sessionId))
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다"))
                .toDomain();
        foundSession.register(payment);
        sessionRepository.save(SessionEntity.from(foundSession), Session.NOT_ASSIGNED);
    }

    @Transactional
    public void apply(String userId, Long sessionId, Payment payment) {
        Session foundSession = getSession(sessionId);
        Student student = new Student(userService.getUser(userId));
        foundSession.apply(student, payment);
        studentRepository.save(StudentEntity.from(student), sessionId);
    }

    private Session getSession(Long sessionId) {
        SessionEntity entity = Optional.ofNullable(sessionRepository.findById(sessionId))
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다"));

        return entity.toDomain();
    }

    @Transactional
    public void select(String userId, Long sessionId) {
        Session foundSession = getSession(sessionId);
        executeRegistrationProcess(foundSession, userId, foundSession::select);
    }

    private void executeRegistrationProcess(Session session, String userId, Function<NsUser, Student> executor) {
        Student registeredStudent = executor.apply(userService.getUser(userId));
        sessionRepository.save(SessionEntity.from(session), Session.NOT_ASSIGNED);
        studentRepository.update(StudentEntity.from(registeredStudent));
    }

    @Transactional
    public void register(String userId, Long sessionId) {
        Session foundSession = getSession(sessionId);
        executeRegistrationProcess(foundSession, userId, foundSession::register);
    }

    @Transactional
    public void reject(String userId, Long sessionId) {
        Session foundSession = getSession(sessionId);
        executeRegistrationProcess(foundSession, userId, foundSession::reject);
    }
}
