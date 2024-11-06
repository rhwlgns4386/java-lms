package nextstep.payments.domain;

import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findBySessionIdAndUserId(Long sessionId, Long userId);
}
