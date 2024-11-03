package nextstep.courses.domain.session;

import java.util.Arrays;

public enum RecruitmentStatus {
    NOT_RECRUITING("not_recruiting", "비모집중"),
    RECRUITING("recruiting", "모집중");

    private final String code;
    private final String description;

    RecruitmentStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static RecruitmentStatus from(String code) {
        return Arrays.stream(values())
                .filter(status -> status.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 모집 상태입니다."));
    }

    public String getCode() {
        return code;
    }
}
