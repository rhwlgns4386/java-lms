package nextstep.courses.domain.strategy;

import java.util.UUID;

public class PaymentStrategyImpl implements PaymentStrategy {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
