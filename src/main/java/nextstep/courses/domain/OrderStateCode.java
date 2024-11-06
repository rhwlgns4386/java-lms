package nextstep.courses.domain;

import java.util.Arrays;

public enum OrderStateCode {
    READY(10),
    APPROVE(20),
    CANCEL(30);

    private final int orderStateCode;

    OrderStateCode(int orderStateCode) {
        this.orderStateCode = orderStateCode;
    }

    public static OrderStateCode fromCode(int orderStateCode) {
        return Arrays.stream(OrderStateCode.values()).filter(value -> value.getOrderStateCode() == orderStateCode)
                .findFirst().orElseThrow(() -> {
                    throw new IllegalArgumentException("주문상태코드가 유효하지 않습니다.");
                });
    }

    public int getOrderStateCode(){
        return orderStateCode;
    }

}
