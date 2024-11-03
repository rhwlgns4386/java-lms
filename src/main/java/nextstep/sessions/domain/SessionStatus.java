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
        // step4. 요구사항 변경으로 인한 로직 추가
        if (state.equals("RECRUITING")) {
            state = "ONGOING";
        }
        if (state.equals("ENDING")) {
            state = "END";
        }
        String finalState = state;
        return Arrays.stream(SessionStatus.values())
                .filter(sessionStatus -> sessionStatus.name().equals(finalState))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 상태가 없습니다."));
    }
}
