package nextstep.courses.domain.session;

import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;

public class SessionStatus {

    private SessionState sessionState;
    private RecruitState recruitState;

    public SessionStatus(SessionState sessionState, RecruitState recruitState) {
        if (sessionState == SessionState.END && recruitState == RecruitState.RECRUIT) {
            throw new IllegalArgumentException("종료된 강의는 모집중 상태가 될 수 없습니다");
        }

        this.sessionState = sessionState;
        this.recruitState = recruitState;
    }

    public boolean canRegister() {
        return sessionState.canRegister() && recruitState.canRegister();
    }

    public SessionState getSessionState() {
        return sessionState;
    }

    public RecruitState getRecruitState() {
        return recruitState;
    }
}
