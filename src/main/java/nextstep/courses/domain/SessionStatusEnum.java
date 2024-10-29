package nextstep.courses.domain;

import java.util.Arrays;

public enum SessionStatusEnum {
    PREPARING("01","준비중",false),
    RECRUITING("02","모집중", true),
    END("03","종료", false);

    private String value;
    private String description;
    private boolean isPeriodForApplication;

    SessionStatusEnum(String value, String description, boolean isPeriodForApplication) {
        this.value = value;
        this.description = description;
        this.isPeriodForApplication = isPeriodForApplication;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPeriodForApplication() {
        System.out.println(isPeriodForApplication);
        return isPeriodForApplication;
    }

    public static SessionStatusEnum getEnumByStatus(String status) {
        return Arrays.stream(values()).filter(e -> e.value.equals(status)).findFirst().orElseThrow();
    }
}
