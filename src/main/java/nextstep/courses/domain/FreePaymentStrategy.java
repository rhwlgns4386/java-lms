package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class FreePaymentStrategy implements PaymentStrategy {

    private final int price = 0;

    public FreePaymentStrategy() {
    }

    @Override
    public boolean payable(Payment payment) {
        return true;
    }

}
