package nextstep.sessions.domain;

import java.util.Arrays;

public enum ApplicationStatusEnum {
    apply("00"),
    approve("01"),
    cancel("99");
    private String statusCode ;

    ApplicationStatusEnum(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public static ApplicationStatusEnum getByValue(String value) {
        return Arrays.stream(values()).filter(e -> e.statusCode.equals(value)).findFirst().orElseThrow();
    }
}
