package nextstep.sessions.domain;

import java.util.Arrays;

public enum ApplicationStatusType {
    APPLY("00"),
    APPROVE("01"),
    CANCEL("99");
    private String statusCode ;

    ApplicationStatusType(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public static ApplicationStatusType getByValue(String value) {
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("신청상태 값이 누락 되었습니다.");
        }
        return Arrays.stream(values()).filter(e -> e.statusCode.equals(value)).findFirst().orElseThrow();
    }
}
