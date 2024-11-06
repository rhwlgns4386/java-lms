package nextstep.courses.domain.session;

import java.util.Arrays;

public enum SessionRecruitmentStatus {
    NOT_RECRUITING("notRecruiting", "비모집중"),
    RECRUITING("recruiting", "모집중");

    private final String code;
    private final String description;

    SessionRecruitmentStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static SessionRecruitmentStatus from(String code) {
        return Arrays.stream(values())
                .filter(status -> status.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 모집 상태입니다."));
    }

    public String getCode() {
        return code;
    }
}
