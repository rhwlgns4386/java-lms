package nextstep.courses.domain.session.enrollment;

import nextstep.courses.CannotApplyException;
import nextstep.payments.domain.Payment;

public class Price {
    private final long price;

    private final PayType payType;

    public Price(long price, PayType payType) {
        payType.isValid(price);
        this.price = price;
        this.payType = payType;
    }

    public void isValid(Payment payment) {
        if (!isSame(payment)) {
            throw new CannotApplyException("결제 금액과 수강료가 일치하지 않습니다.");
        }
    }

    public boolean isSame(Payment payment) {
        return price == payment.getAmount();
    }
}
