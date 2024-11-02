package nextstep.sessions.domain;

import java.util.Arrays;

public enum SessionStatus {

    PREPARING("준비중"),
    ONGOING("진행중"),
    END("종료");

    private String state;

    SessionStatus(String state) {
        this.state = state;
    }

    public boolean isEnd() {
        return this == END;
    }

    public static SessionStatus of(String state) {
        return Arrays.stream(SessionStatus.values())
                .filter(sessionStatus -> sessionStatus.name().equals(state))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 상태가 없습니다."));
    }
}
