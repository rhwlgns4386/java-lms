package nextstep.payments;

import nextstep.courses.domain.PaidSession;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public class Payments {
    private final List<Payment> payments;

    public Payments(List<Payment> payments) {
        this.payments = payments;
    }

    public void payment(Payment payment) {
        payments.add(payment);
    }

    public int getSize() {
        return payments.size();
    }

    public Payment getPayments(PaidSession session, NsUser user) {
        return payments.stream()
                .filter(payment -> payment.isPaidUser(session, user))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("결제한 강의가 아닙니다."));
    }
}
