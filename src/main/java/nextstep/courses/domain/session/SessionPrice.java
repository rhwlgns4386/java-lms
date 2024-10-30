package nextstep.courses.domain.session;

import java.util.Objects;

public class SessionPrice {
    private static final String INVALID_PAYMENT_ERROR = "수강료와 지불 금액이 일치하지 않습니다.";
    private static final String PRICE_NULL_ERROR = "가격은 꼭 입력해야합니다.";
    private static final String INVALID_PRICE_ERROR= "가격은 0 이상이어야 합니다.";

    private final SessionPriceType sessionPriceType;
    private final Long price;

    private SessionPrice(SessionPriceType sessionPriceType, Long price) {
        this.sessionPriceType = sessionPriceType;
        this.price = price;
    }

    public static SessionPrice createSessionPriceOf(SessionPriceType sessionPriceType, Long price) {
        if (price == null) {
            throw new IllegalArgumentException(PRICE_NULL_ERROR);
        }

        if(price < 0) {
            throw new IllegalArgumentException(INVALID_PRICE_ERROR);
        }

        if(sessionPriceType.isFree()){
            price = 0L;
        }

        return new SessionPrice(sessionPriceType, price);
    }

    public SessionPriceType getSessionPriceType() {
        return sessionPriceType;
    }

    public Long getPrice() {
        return price;
    }

    public void checkPaymentPrice(Long paymentPrice) {
        if(sessionPriceType.isFree() || Objects.equals(paymentPrice, price)) {
            return ;
        }
        throw new IllegalArgumentException(INVALID_PAYMENT_ERROR);
    }

}
