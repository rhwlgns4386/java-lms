package nextstep.courses.domain;

import java.math.BigDecimal;

public class SessionType {
    private String typeCd;
    private Long feeAmount;
    private int maxNumberOfUser;

    public SessionType(Long feeAmount , int maxNumberOfUser) {
        this.feeAmount = feeAmount;
        this.typeCd = feeAmount > 0 ?
                SessionTypeEnum.PAID.getTypeCode() : SessionTypeEnum.FREE.getTypeCode();
        this.maxNumberOfUser = maxNumberOfUser;
    }

    public SessionType() {
        this(Long.valueOf(0), 0);
    }

    public void checkMaxNumber(int numberOfCurrentUser) {
        if (typeCd.equals(SessionTypeEnum.FREE.getTypeCode())) {
            return ;
        }
        if (numberOfCurrentUser >= maxNumberOfUser) {
            throw new RuntimeException("수강 가능 최대 인원을 초과하였습니다");
        }
    }

    public void validatePaymentAmount(Long payAmount) {
        if (typeCd.equals(SessionTypeEnum.FREE.getTypeCode())) {
            return ;
        }
        if (payAmount.compareTo(feeAmount) != 0) {
            throw new RuntimeException("수강료를 결제내역을 확인하십시오.");
        }
    }
}
