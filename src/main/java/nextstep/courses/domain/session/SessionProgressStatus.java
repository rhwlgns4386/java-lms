package nextstep.courses.domain.session;

public enum SessionProgressStatus {
    PREPARING("준비중"),
    ON_GOING("진행중"),
    END("종료");

    private final String value;

    SessionProgressStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
