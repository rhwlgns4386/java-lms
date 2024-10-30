package nextstep.sessions.domain;

public class SessionStatus {
    private String status;

    public SessionStatus() {
        this(SessionStatusEnum.PREPARING.getValue());
    }

    public SessionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void isValidStatusForApplication() {
        if (SessionStatusEnum.getEnumByStatus(status).isPeriodForApplication()) {
            return;
        }
        throw new RuntimeException("상태 : " + status + "수강이 불가한 상태입니다");
    }
}
