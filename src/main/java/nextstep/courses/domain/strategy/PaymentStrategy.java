package nextstep.courses.domain.strategy;

import nextstep.payments.domain.Payment;

public interface PaymentStrategy {
    boolean payable(Payment payment);
}
