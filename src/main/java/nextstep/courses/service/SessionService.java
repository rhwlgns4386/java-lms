package nextstep.courses.service;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.image.ImageRepository;
import nextstep.courses.domain.session.*;
import nextstep.courses.domain.student.StudentRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("sessionService")
public class SessionService {
    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;
    @Resource(name = "imageRepository")
    private ImageRepository imageRepository;
    @Resource(name = "studentRepository")
    private StudentRepository studentRepository;

    @Transactional
    public long create(Long courseId, Session session, Image image) {
        long saveSessionId = sessionRepository.save(session, courseId);
        imageRepository.save(image, saveSessionId);

        return saveSessionId;
    }

    @Transactional
    public Session register(Long sessionId, NsUser nsUser, Payment payment) {
        Session session = sessionRepository.findById(sessionId);

        Registration registration = Registration.of(sessionId, nsUser, payment);

        if (session.getSessionType().equals(SessionType.FREE)) {
            registerFreeSession((FreeSession) session, registration);
            return session;
        }
        registerPaidSession((PaidSession) session, registration);
        return session;
    }

    private void registerPaidSession(PaidSession session, Registration registration) {
        session.register(registration);
        studentRepository.saveAll(session.getStudents(), session.getId());
    }

    private void registerFreeSession(FreeSession session, Registration registration) {
        session.register(registration);
        studentRepository.saveAll(session.getStudents(), session.getId());
    }

}
