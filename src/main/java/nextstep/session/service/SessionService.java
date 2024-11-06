package nextstep.session.service;

import org.springframework.stereotype.Service;

import nextstep.enrollment.domain.Enrollment;
import nextstep.enrollment.domain.EnrollmentRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import nextstep.users.domain.NsUser;

@Service
public class SessionService {

    private final PaymentService paymentService;
    private final SessionRepository sessionRepository;
    private final EnrollmentRepository enrollmentRepository;

    public SessionService(PaymentService paymentService, SessionRepository sessionRepository,
        EnrollmentRepository enrollmentRepository) {
        this.paymentService = paymentService;
        this.sessionRepository = sessionRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void enroll(NsUser user, Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));
        session.enroll(getEnrollment(user, session));
    }

    private Enrollment getEnrollment(NsUser user, Session session) {
        if (session.isFree()) {
            return enrollmentRepository.save(Enrollment.free(1L, session, user));
        }
        Payment payment = paymentService.getPaymentHistory(user, session.getId());
        return enrollmentRepository.save(Enrollment.paid(1L, session, user, payment));
    }
}
