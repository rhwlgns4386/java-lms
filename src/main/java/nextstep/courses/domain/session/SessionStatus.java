package nextstep.courses.domain.session;

import nextstep.courses.SessionException;

import java.util.Arrays;

public enum SessionStatus {
    READY, RECRUITING, FINISH;

    public static SessionStatus search(String type) {
        return Arrays.stream(values())
                .filter(status -> status.toString().equals(type))
                .findFirst()
                .orElseThrow(() -> new SessionException("해당 상태는 찾을 수 없습니다"));
    }

    public void validateRegistration() {
        if (this != SessionStatus.RECRUITING) {
            throw new SessionException("모집중이 아니라 수강신청 할 수 없습니다");
        }
    }
}
