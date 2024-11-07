package nextstep.enrollment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nextstep.enrollment.domain.Enrollment;
import nextstep.enrollment.domain.EnrollmentRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import nextstep.users.domain.NsUser;

@Service
public class EnrollmentService {

    private final PaymentService paymentService;
    private final EnrollmentRepository enrollmentRepository;
    private final SessionRepository sessionRepository;

    public EnrollmentService(PaymentService paymentService, EnrollmentRepository enrollmentRepository,
        SessionRepository sessionRepository) {
        this.paymentService = paymentService;
        this.enrollmentRepository = enrollmentRepository;
        this.sessionRepository = sessionRepository;
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
}
