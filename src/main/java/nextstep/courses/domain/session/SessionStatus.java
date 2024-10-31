package nextstep.courses.domain.session;

import nextstep.qna.SessionException;

public enum SessionStatus {
    READY, RECRUITING, FINISH;

    public void validateRegistration() {
        if (this != SessionStatus.RECRUITING) {
            throw new SessionException("모집중이 아니라 수강신청 할 수 없습니다");
        }
    }
}
