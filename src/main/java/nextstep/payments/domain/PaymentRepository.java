package nextstep.payments.domain;

import java.util.Optional;

public interface PaymentRepository {
    void save(Payment payment);
}
