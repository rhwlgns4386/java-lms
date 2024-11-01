package nextstep.sessions.domain;

import nextstep.sessions.CannotRegisterException;

public enum SessionStatus {
    PREPARING(Boolean.FALSE),
    RECRUITING(Boolean.TRUE),
    ENDED(Boolean.FALSE);

    private final Boolean registerAvailable;

    SessionStatus(Boolean registerAvailable) {
        this.registerAvailable = registerAvailable;
    }

    public void checkRegisterAvailable() {
        if (Boolean.FALSE.equals(this.registerAvailable)) {
            throw new CannotRegisterException("강의 수강 신청은 강의 상태가 모집중일 때만 가능합니다.");
        }
    }
}
