package nextstep.sessions.domain;

import java.util.Arrays;

public enum SessionStatusType {
    PREPARING("01","준비중",false),
    RECRUITING("02","모집중", true),
    END("03","종료", false);

    private String value;
    private String description;
    private boolean isStatusAvailableForApplication;

    SessionStatusType(String value, String description, boolean isStatusAvailableForApplication) {
        this.value = value;
        this.description = description;
        this.isStatusAvailableForApplication = isStatusAvailableForApplication;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStatusAvailableForApplication() {
        return isStatusAvailableForApplication;
    }

    public static SessionStatusType getEnumByStatus(String status) {
        return Arrays.stream(values()).filter(e -> e.value.equals(status)).findFirst().orElseThrow();
    }
}
