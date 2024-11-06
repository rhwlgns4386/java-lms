package nextstep.sessions.domain;

import java.util.Arrays;

public enum SessionProgressStatusType {
    PREPARING("01"),
    PROGRESSING("02"),
    END("03");

    private String statusCode ;
    SessionProgressStatusType(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public static SessionProgressStatusType getByStatusCode(String statusCode) {
        return Arrays.stream(values()).filter(e -> e.statusCode.equals(statusCode)).findFirst().orElseThrow();
    }
}
