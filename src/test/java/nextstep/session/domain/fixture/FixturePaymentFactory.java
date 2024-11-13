package nextstep.session.domain.fixture;

import java.time.LocalDateTime;

import nextstep.payments.domain.Payment;

public class FixturePaymentFactory {

    private FixturePaymentFactory() {
    }

    public static Payment create(Long id, Long sessionId, Long userId, Long amount) {
        return new Payment(id, sessionId, userId, amount, LocalDateTime.now());
    }
}
