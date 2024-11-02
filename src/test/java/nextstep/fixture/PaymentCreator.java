package nextstep.fixture;

import nextstep.payments.domain.Payment;

public class PaymentCreator {

    public static Payment pay(long amount) {
        return new Payment("1", 1L, 1L, amount);
    }
}
