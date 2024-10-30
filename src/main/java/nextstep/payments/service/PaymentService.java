package nextstep.payments.service;

import nextstep.payments.domain.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public Payment payment() {
        // PG사 API를 통해 id에 해당하는 결제 정보를 반환
        return new Payment();
    }
}
