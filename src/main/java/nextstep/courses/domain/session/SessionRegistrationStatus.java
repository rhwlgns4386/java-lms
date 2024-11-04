package nextstep.courses.domain.session;

import java.util.Arrays;

public enum SessionRegistrationStatus {
    PENDING("PENDING", "승인대기"),
    APPROVED("APPROVED", "승인"),
    CANCEL("CANCEL", "취소");

    private final String code;
    private final String description;

    SessionRegistrationStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static SessionRegistrationStatus from(String code) {
        return Arrays.stream(values())
                .filter(status -> status.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 등록 상태 입니다."));
    }

    public String getCode() {
        return code;
    }
}
