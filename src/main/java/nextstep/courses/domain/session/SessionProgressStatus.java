package nextstep.courses.domain.session;

import nextstep.courses.SessionException;

import java.util.Arrays;

public enum SessionProgressStatus {
    READY, IN_PROGRESS, FINISH;

    public static SessionProgressStatus search(String text) {
        return Arrays.stream(values())
                .filter(status -> status.toString().equals(text))
                .findFirst()
                .orElseThrow(
                        () -> new SessionException("없는 진행 상태입니다")
                );
    }

    public boolean isApply() {
        return this == SessionProgressStatus.IN_PROGRESS;
    }
}
