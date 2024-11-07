package nextstep.session.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nextstep.enrollment.domain.Enrollment;
import nextstep.enrollment.domain.EnrollmentRepository;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionPolicy;
import nextstep.session.domain.SessionRepository;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final List<SessionPolicy> sessionPolicies;

    public SessionService(SessionRepository sessionRepository, EnrollmentRepository enrollmentRepository,
        List<SessionPolicy> sessionPolicies) {
        this.sessionRepository = sessionRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.sessionPolicies = sessionPolicies;
    }

    @Transactional(readOnly = true)
    public void approve(Long enrollmentId, Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
            .orElseThrow(() -> new IllegalArgumentException("수강신청 정보를 찾을 수 없습니다."));

        sessionPolicies.stream()
            .filter(it -> it.isMatch(session.getSessionType()))
            .findFirst()
            .ifPresent(it -> it.validatePolicy(session, enrollment));
        enrollment.approve();
        session.enroll(enrollment);
    }

}
