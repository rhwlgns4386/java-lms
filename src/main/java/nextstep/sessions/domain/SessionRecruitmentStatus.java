package nextstep.sessions.domain;

import nextstep.sessions.CannotRegisterException;

public enum SessionRecruitmentStatus {
    CLOSED(false),
    OPEN(true);

    private final boolean registerAvailable;

    SessionRecruitmentStatus(boolean registerAvailable) {
        this.registerAvailable = registerAvailable;
    }

    public void checkRegisterAvailable() {
        if (!this.registerAvailable) {
            throw new CannotRegisterException("강의 모집 상태가 '모집 중'이 아닐 때 강의 수강 신청이 불가합니다.");
        }
    }
}
