package nextstep.courses.domain.session;

public class SessionStatus {
    private final SessionProgress progress;
    private final RecruitmentStatus recruitment;

    public SessionStatus(SessionProgress progress, RecruitmentStatus recruitment) {
        this.progress = progress;
        this.recruitment = recruitment;
    }

    public static SessionStatus ready() {
        return new SessionStatus(SessionProgress.READY, RecruitmentStatus.NOT_RECRUITING);
    }

    public SessionStatus inProgress() {
        return new SessionStatus(SessionProgress.IN_PROGRESS, this.recruitment);
    }

    public SessionStatus finished() {
        return new SessionStatus(SessionProgress.FINISHED, this.recruitment);
    }

    public SessionStatus recruiting() {
        return new SessionStatus(this.progress, RecruitmentStatus.RECRUITING);
    }

    public SessionStatus notRecruiting() {
        return new SessionStatus(this.progress, RecruitmentStatus.NOT_RECRUITING);
    }

    public boolean canRegister() {
        return recruitment != RecruitmentStatus.NOT_RECRUITING && progress != SessionProgress.FINISHED;
    }

    public String getProgressCode() {
        return progress.getCode();
    }

    public String getRecruitmentCode() {
        return recruitment.getCode();
    }
}
