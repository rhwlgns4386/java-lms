package nextstep.sessions.service;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import nextstep.payments.domain.Payment;
import nextstep.registration.domain.SessionRegistrationInfo;
import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.SessionImage;
import nextstep.sessions.domain.SessionImageRepository;
import nextstep.sessions.domain.SessionRegistrationInfoRepository;
import nextstep.sessions.domain.SessionRepository;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final CourseRepository courseRepository;
    private final SessionImageRepository sessionImageRepository;
    private final SessionRegistrationInfoRepository sessionRegistrationInfoRepository;

    public SessionService(SessionRepository sessionRepository, CourseRepository courseRepository, SessionImageRepository sessionImageRepository, SessionRegistrationInfoRepository sessionRegistrationInfoRepository) {
        this.sessionRepository = sessionRepository;
        this.courseRepository = courseRepository;
        this.sessionImageRepository = sessionImageRepository;
        this.sessionRegistrationInfoRepository = sessionRegistrationInfoRepository;
    }

    @Transactional
    public void enroll(Long sessionId, NsUser user, Payment payment) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 세션을 찾을 수 없습니다."));
        Course course = courseRepository.findById(session.getCourseId());
        SessionImage image = sessionImageRepository.findById(session.getImageId());
        session.setCourse(course);
        session.setImage(image);
        session.enroll(user, payment);
        sessionRepository.update(session);
        if (session.getEnrolledUserInfosSize() > 0) {
            sessionRegistrationInfoRepository.save(new SessionRegistrationInfo(session, user));
        }
    }
}
