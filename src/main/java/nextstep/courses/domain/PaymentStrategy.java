package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public interface PaymentStrategy {
    boolean payable(Payment payment);
}
