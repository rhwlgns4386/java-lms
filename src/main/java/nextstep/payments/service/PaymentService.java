package nextstep.payments.service;

import nextstep.payments.domain.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public Payment payment(String id) {
        return new Payment();
    }

}
