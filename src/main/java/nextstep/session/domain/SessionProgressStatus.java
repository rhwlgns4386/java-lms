package nextstep.session.domain;

public enum SessionProgressStatus {
    PREPARING("준비중"),
    IN_PROGRESS("진행중"),
    COMPLETED("종료");

    private final String description;

    SessionProgressStatus(String description) {
        this.description = description;
    }
}
