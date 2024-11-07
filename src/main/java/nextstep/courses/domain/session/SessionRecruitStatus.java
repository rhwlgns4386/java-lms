package nextstep.courses.domain.session;

import nextstep.courses.SessionException;

import java.util.Arrays;

public enum SessionRecruitStatus {
    NON_RECRUITMENT, RECRUITING;

    public static SessionRecruitStatus search(String text) {
        return Arrays.stream(values())
                .filter(status -> status.toString().equals(text))
                .findFirst()
                .orElseThrow(
                        () -> new SessionException("지원하지 않는 모집 상태입니다")
                );
    }

    public boolean isApply() {
        return this == RECRUITING;
    }
}
