package nextstep.courses.service;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.StudentRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sessionService")
public class SessionService {
    private SessionRepository sessionRepository;

    private StudentRepository studentRepository;

    private UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository, StudentRepository studentRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void enroll(long sessionId, NsUser student, Payment payment) {
        Session session = sessionRepository.findByIdForSession(sessionId);

        session.enroll(student, payment);

        studentRepository.save(student, sessionId);
    }

}
