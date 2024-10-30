package nextstep.sessions.domain;

import java.util.Arrays;

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

    public static SessionStatus of(String state) {
        return Arrays.stream(SessionStatus.values())
                .filter(sessionStatus -> sessionStatus.name().equals(state))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 상태가 없습니다."));
    }
}
