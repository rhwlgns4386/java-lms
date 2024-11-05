package nextstep.session.service;

import org.springframework.stereotype.Service;

import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.session.domain.Enrollment;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import nextstep.users.domain.NsUser;

@Service
public class SessionService {

    private final PaymentService paymentService;
    private final SessionRepository sessionRepository;

    public SessionService(PaymentService paymentService, SessionRepository sessionRepository) {
        this.paymentService = paymentService;
        this.sessionRepository = sessionRepository;
    }

    public void enroll(NsUser user, Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다."));

        if (session.isFree()) {
            session.enroll(new Enrollment(1L, session, user, null));
        } else {
            Payment payment = paymentService.payment("결제 id"); // 결제에 대한 정책이 없으므로 ID를 임시 하드코딩함
            session.enroll(new Enrollment(1L, session, user, payment));
        }
    }
}
