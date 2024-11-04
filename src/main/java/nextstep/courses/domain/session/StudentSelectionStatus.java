package nextstep.courses.domain.session;

import java.util.Arrays;

public enum StudentSelectionStatus {
    PENDING("PENDING", "선발 대기중"),
    SELECTED("SELECTED", "선발"),
    NOT_SELECTED("NOT_SELECTED", "미선발");

    private final String code;
    private final String description;

    StudentSelectionStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static StudentSelectionStatus from(String code) {
        return Arrays.stream(values())
                .filter(status -> status.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 선발 상태입니다."));
    }

    public String getCode() {
        return code;
    }
}
