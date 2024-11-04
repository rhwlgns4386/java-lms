package nextstep.courses.domain.session;

import java.util.Arrays;

public enum SessionRecruitStatus {
    NON_RECRUITMENT("비모집중"),
    RECRUITMENT("모집중");

    private final String value;

    SessionRecruitStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SessionRecruitStatus of(String name) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
