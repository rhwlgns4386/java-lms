package nextstep.payments.service;

import org.springframework.stereotype.Service;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

@Service
public class PaymentService {
    public Payment payment(String id) {
        // PG사 API를 통해 id에 해당하는 결제 정보를 반환
        return new Payment();
    }

    public Payment getPaymentHistory(NsUser nsUser, Long session_id) {
        // PG사 API를 통해 id에 해당하는 결제 내역 정보를 반환
        return new Payment();
    }
}
