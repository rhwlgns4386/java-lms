package nextstep.payments.domain;

import java.util.ArrayList;
import java.util.List;

public class Payments {
    private final List<Payment> payments;

    public Payments() {
        this.payments = new ArrayList<>();
    }

    public Payments(List<Payment> payments) {
        this.payments = payments;
    }

    public void addPayments(Payment payment) {
        payments.add(payment);
    }

    public List<Payment> getPayments() {
        return payments;
    }

}
