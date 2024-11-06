package nextstep.courses.domain;

public class Status {
    private final ProgressStatus progressStatus;
    private final RecruitmentStatus recruitmentStatus;

    public Status(ProgressStatus progressStatus, RecruitmentStatus recruitmentStatus) {
        this.progressStatus = progressStatus;
        this.recruitmentStatus = recruitmentStatus;
    }

    public boolean canEnroll(){
        return progressStatus.isNotFinished() && recruitmentStatus.canEnroll();
    }

    public boolean canNotEnroll(){
        return !canEnroll();
    }

    public ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public RecruitmentStatus getRecruitmentStatus() {
        return recruitmentStatus;
    }
}
