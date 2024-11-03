package nextstep.courses.domain;

import java.util.Arrays;

public enum StateCode {
    READY(10),
    RECRUITING(20),
    END(30);

    private final int statusCode;

    StateCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    public static StateCode fromCode(long statusCode) {
        return Arrays.stream(StateCode.values())
                .filter(value -> value.getStatusCode() == statusCode)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 상태 코드입니다: " + statusCode));
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void validateOrderSessionStatus() {
        if (StateCode.RECRUITING != this) {
            throw new IllegalArgumentException("모집하지 않는 강의입니다.");
        }
    }

}
