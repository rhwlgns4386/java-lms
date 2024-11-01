package nextstep.sessions.domain;

public class SessionStatus {

    private SessionStatusEnum status;

    public SessionStatus() {
        this(SessionStatusEnum.PREPARING);
    }

    public SessionStatus(SessionStatusEnum status) {
        this.status = status;
    }

    public String getStatus() {
        return status.getValue();
    }

    public void isValidStatusForApplication() {
        if (status.isStatusAvailableForApplication()) {
            return;
        }
        throw new RuntimeException("상태 : " + status + "수강이 불가한 상태입니다");
    }
}
