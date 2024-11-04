package nextstep.sessions.service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.payments.domain.Payment;
import nextstep.registration.domain.SessionRegistrationInfo;
import nextstep.sessions.domain.Session;
import nextstep.registration.domain.SessionRegistrationInfoRepository;
import nextstep.sessions.domain.SessionImageRepository;
import nextstep.sessions.domain.SessionRepository;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionImageRepository sessionImageRepository;
    private final CourseRepository courseRepository;
    private final SessionRegistrationInfoRepository sessionRegistrationInfoRepository;

    public SessionService(SessionRepository sessionRepository, SessionImageRepository sessionImageRepository, CourseRepository courseRepository, SessionRegistrationInfoRepository sessionRegistrationInfoRepository) {
        this.sessionRepository = sessionRepository;
        this.sessionImageRepository = sessionImageRepository;
        this.courseRepository = courseRepository;
        this.sessionRegistrationInfoRepository = sessionRegistrationInfoRepository;
    }

    @Transactional
    public void createSession(Session session) {
        sessionRepository.save(session);
        session.getImages().forEach(i -> sessionImageRepository.saveWithSessionId(i, session.getId()));
    }

    @Transactional
    public void enroll(Long sessionId, NsUser user, Payment payment) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 세션을 찾을 수 없습니다."));
        Course course = courseRepository.findById(session.getCourseId());
        session.setCourse(course);
        session.enroll(user, payment);
        sessionRepository.update(session);
        if (session.getEnrolledUserInfosSize() > 0) {
            sessionRegistrationInfoRepository.save(new SessionRegistrationInfo(session, user));
        }
    }
}
