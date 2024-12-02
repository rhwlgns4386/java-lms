package nextstep.courses.service;

import java.util.Set;
import javax.annotation.Resource;
import nextstep.courses.EntityNotFoundException;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.EnrollmentStudentRepository;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Session2Service {

    @Resource(name = "session2Repository")
    private SessionRepository sessionRepository;

    @Resource(name = "enrollmentStudentRepository")
    private EnrollmentStudentRepository enrollmentStudentRepository;

    @Transactional
    public void enroll(long sessionId, int fee, NsUser user) {
        Session session = findById(sessionId);
        Set<EnrollmentStudent> enrollmentStudents = enrollmentStudentRepository.findBySessionId(sessionId);

        Enrollments enrollments = session.enrollments(fee, enrollmentStudents);
        enrollments.enrollment(session, user);
        enrollmentStudentRepository.update(enrollments);
    }

    @Transactional
    public void enroll2(long sessionId, int fee, NsUser user) {
        Session session = findById(sessionId);
        Set<EnrollmentStudent> enrollmentStudents = enrollmentStudentRepository.findBySessionId(sessionId);

        Enrollments enrollments = session.enrollments(fee, enrollmentStudents, user);
        enrollmentStudentRepository.update(enrollments);
    }

    @Transactional(readOnly = true)
    public Session findById(long sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException(Session.class));
    }
}
