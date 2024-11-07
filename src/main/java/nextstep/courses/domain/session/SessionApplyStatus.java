package nextstep.courses.domain.session;

import nextstep.courses.SessionException;

public class SessionApplyStatus {
    private SessionProgressStatus progressStatus;
    private SessionRecruitStatus recruitStatus;

    public SessionApplyStatus(SessionProgressStatus progressStatus, SessionRecruitStatus recruitStatus) {
        this.progressStatus = progressStatus;
        this.recruitStatus = recruitStatus;
    }

    void validate() {
        if (!progressStatus.isApply() && !recruitStatus.isApply()) {
            throw new SessionException("수강 신청을 할 수 없습니다.");
        }
    }

    public SessionProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public SessionRecruitStatus getRecruitStatus() {
        return recruitStatus;
    }

    public void recruiting() {
        this.recruitStatus = SessionRecruitStatus.RECRUITING;
    }

    public void finish() {
        this.progressStatus = SessionProgressStatus.FINISH;
    }
}
