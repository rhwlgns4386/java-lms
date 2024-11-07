package nextstep.session.domain;

public enum SessionProgressStatus {
    PREPARING("진행중"),
    IN_PROGRESS("모집중"),
    COMPLETED("종료");

    private final String description;

    SessionProgressStatus(String description) {
        this.description = description;
    }
}
