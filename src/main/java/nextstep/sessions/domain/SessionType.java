package nextstep.sessions.domain;

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

    public void validatePaymentAmount(Long payAmount) {
        if (sessionType == SessionTypeEnum.FREE ) {
            return ;
        }
        if (payAmount.compareTo(feeAmount) != 0) {
            throw new RuntimeException("수강료를 결제내역을 확인하십시오.");
        }
    }
}
