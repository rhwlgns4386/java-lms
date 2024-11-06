package nextstep.payments.service;

import org.springframework.stereotype.Service;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import nextstep.users.domain.NsUser;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment payment(String id) {
        // PG사 API를 통해 id에 해당하는 결제 정보를 반환
        return new Payment();
    }

    public Payment getPaymentHistory(NsUser nsUser, Long session_id) {
        return paymentRepository.findBySessionIdAndUserId(session_id, nsUser.getId())
            .orElseThrow(() -> new IllegalArgumentException("결제정보를 찾을 수 없습니다."));
    }
}
