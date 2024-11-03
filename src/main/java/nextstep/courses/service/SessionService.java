package nextstep.courses.service;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.image.ImageRepository;
import nextstep.courses.domain.session.*;
import nextstep.courses.domain.student.Student;
import nextstep.courses.domain.student.StudentRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final ImageRepository imageRepository;
    private final StudentRepository studentRepository;

    public SessionService(SessionRepository sessionRepository, ImageRepository imageRepository, StudentRepository studentRepository) {
        this.sessionRepository = sessionRepository;
        this.imageRepository = imageRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public Session findById(long sessionId) {
        Session session = sessionRepository.findById(sessionId);
        Image image = imageRepository.findBySessionId(sessionId);
        List<Student> students = studentRepository.findAllBySessionId(sessionId);

        return getSession(session, image, students);
    }

    private static Session getSession(Session session, Image image, List<Student> students) {
        if (session.getSessionType().equals(SessionType.FREE)) {
            return FreeSession.of((FreeSession) session, image, students);
        }
        return PaidSession.of((PaidSession) session, image, students);
    }

    @Transactional(readOnly = true)
    public List<Session> findAllByCourseId(long courseId) {
        return sessionRepository.findAllByCourseId(courseId).stream()
                .map(it -> {
                    Image image = imageRepository.findBySessionId(it.getId());
                    List<Student> students = studentRepository.findAllBySessionId(it.getId());

                    return getSession(it, image, students);
                })
                .collect(Collectors.toList());
    }

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
