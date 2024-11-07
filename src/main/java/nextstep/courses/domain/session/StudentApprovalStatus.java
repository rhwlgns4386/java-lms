package nextstep.courses.domain.session;

import java.util.Arrays;

public enum StudentApprovalStatus {
    PENDING("PENDING", "선발 대기중"),
    APPROVED("APPROVED", "선발"),
    REJECTED("REJECTED", "미선발");

    private final String code;
    private final String description;

    StudentApprovalStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static StudentApprovalStatus from(String code) {
        return Arrays.stream(values())
                .filter(status -> status.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 선발 상태입니다."));
    }

    public String getCode() {
        return code;
    }
}
