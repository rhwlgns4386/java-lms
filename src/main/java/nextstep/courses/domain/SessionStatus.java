package nextstep.courses.domain;

import java.util.Arrays;

public enum SessionStatus {
    PREPARING,
    ENROLLING,
    CLOSED;

    public static SessionStatus findByName(String name) {
        return Arrays.stream(values()).filter((status) -> status.name().equalsIgnoreCase(name)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("세션 상태를 찾을 수 없습니다"));
    }

    public boolean isEnrolling() {
        return this == ENROLLING;
    }
}
