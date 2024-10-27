package nextstep.sessions.domain;

public enum SessionStatus {

    PREPARING("준비중"),
    RECRUITING("모집중"),
    ENDING("종료");

    private String state;

    SessionStatus(String state) {
        this.state = state;
    }

    public boolean isRecruiting() {
        return this == RECRUITING;
    }
}
