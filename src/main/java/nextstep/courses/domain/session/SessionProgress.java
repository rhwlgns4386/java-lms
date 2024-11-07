package nextstep.courses.domain.session;

import java.util.Arrays;

public enum SessionProgress {
    READY("ready", "준비중"),
    IN_PROGRESS("inProgress", "진행중"),
    FINISHED("finished", "종료");

    private final String code;
    private final String description;

    SessionProgress(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static SessionProgress from(String code) {
        return Arrays.stream(values())
                .filter(progress -> progress.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 진행 상태입니다."));
    }

    public String getCode() {
        return code;
    }
}
