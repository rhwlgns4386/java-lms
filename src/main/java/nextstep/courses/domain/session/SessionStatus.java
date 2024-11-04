package nextstep.courses.domain.session;

public class SessionStatus {
    private SessionProgressStatus sessionProgressStatus;
    private SessionRecruitStatus sessionRecruitStatus;

    public SessionStatus(SessionProgressStatus sessionProgressStatus, SessionRecruitStatus sessionRecruitStatus) {
        this.sessionProgressStatus = sessionProgressStatus;
        this.sessionRecruitStatus = sessionRecruitStatus;
    }

    public static SessionStatus init() {
        return new SessionStatus(SessionProgressStatus.PREPARING, SessionRecruitStatus.NON_RECRUITMENT);
    }

    public SessionProgressStatus getSessionProgressStatus() {
        return sessionProgressStatus;
    }

    public SessionRecruitStatus getSessionRecruitStatus() {
        return sessionRecruitStatus;
    }
}
