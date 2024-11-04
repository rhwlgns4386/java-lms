package nextstep.fixture;

import nextstep.payments.domain.Payment;

public class PaymentCreator {

    public static Payment user(Long userId) {
        return new Payment("1", 1L, userId, 0L);
    }

    public static Payment pay(long amount) {
        return new Payment("1", 1L, 1L, amount);
    }
}
