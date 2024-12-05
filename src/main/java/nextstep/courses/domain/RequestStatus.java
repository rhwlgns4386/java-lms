package nextstep.courses.domain;

import java.util.Arrays;

public enum RequestStatus {
    PENDING, ACCEPTED, REJECT;

    public static RequestStatus findByName(String requestStatus) {
        return Arrays.stream(values()).filter((v) -> v.name().equalsIgnoreCase(requestStatus)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("강의 요청 상태를 찾을 수 없습니다"));
    }
}
