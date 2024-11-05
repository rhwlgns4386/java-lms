package nextstep.courses.domain.session;

public class SessionStatus {
    private SessionProgressStatus sessionProgressStatus;
    private SessionRecruitStatus sessionRecruitStatus;

    public SessionStatus(SessionProgressStatus sessionProgressStatus, SessionRecruitStatus sessionRecruitStatus) {
        if (sessionProgressStatus == null || sessionRecruitStatus == null) {
            throw new NullPointerException("sessionProgressStatus or sessionRecruitStatus must not be null");
        }
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

    public void openSession() {
        this.sessionProgressStatus = SessionProgressStatus.ON_GOING;
    }

    public void endSession() {
        this.sessionProgressStatus = SessionProgressStatus.END;
    }

    public void startRecruiting() {
        this.sessionRecruitStatus = SessionRecruitStatus.RECRUITMENT;
    }

    public void finishRecruiting() {
        this.sessionRecruitStatus = SessionRecruitStatus.NON_RECRUITMENT;
    }

    public boolean isApplicationAvailable() {
        return this.sessionRecruitStatus == SessionRecruitStatus.RECRUITMENT && this.sessionProgressStatus != SessionProgressStatus.END;
    }

    public boolean isEnd() {
        return this.sessionProgressStatus == SessionProgressStatus.END;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionStatus)) {
            return false;
        }

        SessionStatus that = (SessionStatus) o;
        return getSessionProgressStatus() == that.getSessionProgressStatus() && getSessionRecruitStatus() == that.getSessionRecruitStatus();
    }

    @Override
    public int hashCode() {
        int result = getSessionProgressStatus().hashCode();
        result = 31 * result + getSessionRecruitStatus().hashCode();
        return result;
    }
}
