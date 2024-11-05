package nextstep.sessions.domain;

import java.util.Arrays;

public enum SessionProgressStatusEnum {
    PREPARING("01"),
    PROGRESSING("02"),
    END("03");

    private String statusCode ;
    SessionProgressStatusEnum(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public static SessionProgressStatusEnum getByStatusCode(String statusCode) {
        return Arrays.stream(values()).filter(e -> e.statusCode.equals(statusCode)).findFirst().orElseThrow();
    }
}
