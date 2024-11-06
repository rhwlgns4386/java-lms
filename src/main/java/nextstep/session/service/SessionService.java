package nextstep.session.service;

import java.util.List;

import org.springframework.stereotype.Service;

import nextstep.enrollment.domain.Enrollment;
import nextstep.enrollment.domain.EnrollmentRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionPolicy;
import nextstep.session.domain.SessionRepository;
import nextstep.users.domain.NsUser;

@Service
public class SessionService {

    private final PaymentService paymentService;
    private final SessionRepository sessionRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final List<SessionPolicy> sessionPolicies;

    public SessionService(PaymentService paymentService, SessionRepository sessionRepository,
        EnrollmentRepository enrollmentRepository, List<SessionPolicy> sessionPolicies) {
        this.paymentService = paymentService;
        this.sessionRepository = sessionRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.sessionPolicies = sessionPolicies;
    }

    public void enroll(NsUser user, Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));
        Enrollment enrollment = getEnrollment(user, session);

        sessionPolicies.stream()
            .filter(it -> it.isMatch(session.getSessionType()))
            .findFirst()
            .ifPresent(it -> it.validatePolicy(session, enrollment));

        session.enroll(enrollment);
    }

    private Enrollment getEnrollment(NsUser user, Session session) {
        if (session.isFree()) {
            return enrollmentRepository.save(Enrollment.free(1L, session, user));
        }
        Payment payment = paymentService.getPaymentHistory(user, session.getId());
        return enrollmentRepository.save(Enrollment.paid(1L, session, user, payment));
    }
}
