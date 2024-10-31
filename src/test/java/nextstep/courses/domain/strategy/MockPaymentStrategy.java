package nextstep.courses.domain.strategy;

public class MockPaymentStrategy implements PaymentStrategy{
    @Override
    public String generate() {
        return "payment1";
    }
}
