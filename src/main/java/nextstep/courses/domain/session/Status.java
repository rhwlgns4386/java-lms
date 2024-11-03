package nextstep.courses.domain.session;

import java.util.Arrays;

public enum Status {
    READY("ready"),
    OPEN("open"),
    CLOSED("closed");

    private final String code;

    Status(String code) {
        this.code = code;
    }

    public static Status from(String status) {
        return Arrays.stream(Status.values())
                .filter(t -> t.code.equals(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 세션 상태입니다."));
    }

    public boolean isOpen() {
        return this == OPEN;
    }

    public String getCode() {
        return code;
    }
}
