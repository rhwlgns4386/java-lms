package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class Premium {

    private final int sessionAmount;
    private boolean isPremium;

    public Premium(boolean isPremium,int sessionAmount) {
        this.isPremium = isPremium;
        this.sessionAmount = sessionAmount;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void validateAmount(Payment payment) {
        if (isPremium && !payment.matchingAmount(sessionAmount)) {
            throw new IllegalArgumentException("강의 금액과 맞지 않습니다.");
        }
    }
}
