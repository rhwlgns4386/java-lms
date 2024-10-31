package nextstep.sessions.domain;

import nextstep.payments.domain.Payment;

public class SessionType {
    private SessionTypeEnum sessionType;
    private Long feeAmount;
    private int maxNumberOfUser;

    public SessionType(Long feeAmount , int maxNumberOfUser) {
        this.feeAmount = feeAmount;
        this.sessionType = feeAmount > 0 ?
                SessionTypeEnum.PAID : SessionTypeEnum.FREE;
        this.maxNumberOfUser = maxNumberOfUser;
    }

    public SessionType() {
        this(Long.valueOf(0), 0);
    }

    public void checkMaxNumber(int numberOfCurrentUser) {
        if (sessionType == SessionTypeEnum.FREE) {
            return ;
        }
        if (numberOfCurrentUser >= maxNumberOfUser) {
            throw new RuntimeException("수강 가능 최대 인원을 초과하였습니다");
        }
    }

    public void validatePaymentAmount(Payment payment) {
        if (sessionType == SessionTypeEnum.FREE ) {
            return ;
        }
        if (payment.getAmount().compareTo(feeAmount) != 0) {
            throw new RuntimeException("결제금액 오류 입니다.");
        }
    }

    public int getMaxNumberOfUser() {
        return maxNumberOfUser;
    }

    public Long getFeeAmount() {
        return feeAmount;
    }

    public String getTypeCode() {
        return sessionType.getTypeCode();
    }

    public boolean isFree() {
        return sessionType == SessionTypeEnum.FREE;
    }

}
