package nextstep.courses.service;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.image.ImageRepository;
import nextstep.courses.domain.lecturer.Lecturer;
import nextstep.courses.domain.lecturer.LecturerRepository;
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
    private final LecturerRepository lecturerRepository;

    public SessionService(SessionRepository sessionRepository,
                          ImageRepository imageRepository,
                          StudentRepository studentRepository,
                          LecturerRepository lecturerRepository) {
        this.sessionRepository = sessionRepository;
        this.imageRepository = imageRepository;
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Transactional(readOnly = true)
    public Session findById(long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow();
        List<Image> images = imageRepository.findAllBySessionId(sessionId);
        List<Student> students = studentRepository.findAllBySessionId(sessionId);
        Lecturer lecturer = lecturerRepository.findBySessionId(sessionId).orElse(null);

        return getSession(session, images, students, lecturer);
    }

    private static Session getSession(Session session, List<Image> images, List<Student> students, Lecturer lecturer) {
        if (session.getSessionType().equals(SessionType.FREE)) {
            return FreeSession.of((FreeSession) session, images, students, lecturer);
        }
        return PaidSession.of((PaidSession) session, images, students, lecturer);
    }

    @Transactional(readOnly = true)
    public List<Session> findAllByCourseId(long courseId) {
        return sessionRepository.findAllByCourseId(courseId).stream()
                .map(it -> {
                    List<Image> images = imageRepository.findAllBySessionId(it.getId());
                    List<Student> students = studentRepository.findAllBySessionId(it.getId());
                    Lecturer lecturer = lecturerRepository.findBySessionId(it.getId()).orElse(null);
                    return getSession(it, images, students, lecturer);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public long create(Long courseId, Session session, List<Image> images, Lecturer lecturer) {
        long saveSessionId = sessionRepository.save(session, courseId);
        imageRepository.saveAll(images, saveSessionId);
        lecturerRepository.save(lecturer, saveSessionId);

        return saveSessionId;
    }

    @Transactional
    public Session register(Long sessionId, NsUser nsUser, Payment payment) {
        Session session = findById(sessionId);

        Registration registration = Registration.of(sessionId, nsUser, payment);

        if (session.getSessionType().equals(SessionType.FREE)) {
            registerFreeSession((FreeSession) session, registration);
            return session;
        }
        registerPaidSession((PaidSession) session, registration);
        return session;
    }

    @Transactional
    public Session accept(Long sessionId, Lecturer lecturer, List<Student> students) {
        Session session = findById(sessionId);
        session.acceptStudents(lecturer, students);

        studentRepository.saveAll(students, sessionId);
        return session;
    }

    @Transactional
    public Session reject(Long sessionId, Lecturer lecturer, List<Student> students) {
        Session session = findById(sessionId);
        session.rejectStudents(lecturer, students);

        studentRepository.saveAll(students, sessionId);
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
