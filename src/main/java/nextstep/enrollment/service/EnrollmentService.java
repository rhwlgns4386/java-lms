package nextstep.enrollment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nextstep.enrollment.domain.Enrollment;
import nextstep.enrollment.domain.EnrollmentRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionPolicy;
import nextstep.session.domain.SessionRepository;
import nextstep.users.domain.NsUser;

@Service
public class EnrollmentService {

    private final PaymentService paymentService;
    private final EnrollmentRepository enrollmentRepository;
    private final SessionRepository sessionRepository;
    private final List<SessionPolicy> sessionPolicies;

    public EnrollmentService(PaymentService paymentService, EnrollmentRepository enrollmentRepository,
        SessionRepository sessionRepository, List<SessionPolicy> sessionPolicies) {
        this.paymentService = paymentService;
        this.enrollmentRepository = enrollmentRepository;
        this.sessionRepository = sessionRepository;
        this.sessionPolicies = sessionPolicies;
    }

    @Transactional
    public Enrollment enrollment(NsUser user, Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));
        if (session.isFree()) {
            return enrollmentRepository.save(Enrollment.free(1L, session, user));
        }
        Payment payment = paymentService.getPaymentHistory(user, session.getId());

        return enrollmentRepository.save(Enrollment.paid(1L, session, user, payment));
    }

    @Transactional(readOnly = true)
    public void approve(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
            .orElseThrow(() -> new IllegalArgumentException("수강신청 정보를 찾을 수 없습니다."));

        validateSessionPolicies(enrollment);

        enrollment.approve();
    }

    private void validateSessionPolicies(Enrollment enrollment) {
        sessionPolicies.stream()
            .filter(it -> it.isMatch(enrollment.getSessionType()))
            .findFirst()
            .ifPresent(it -> it.validatePolicy(enrollment));
    }
}
