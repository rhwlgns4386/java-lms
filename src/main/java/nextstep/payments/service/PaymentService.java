package nextstep.payments.service;

import nextstep.courses.dto.SessionPaymentInfo;
import nextstep.payments.domain.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public Payment payment(String id, Long nsUserId, SessionPaymentInfo sessionPaymentInfo) {
        // PG사 API를 통해 id에 해당하는 결제 정보를 반환
        return new Payment(id, sessionPaymentInfo.getSessionId(), nsUserId, sessionPaymentInfo.getSessionFee());
    }
}
