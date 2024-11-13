package nextstep.courses.domain;

public enum SessionRecruitment {
    RECRUITING, NOT_RECRUITING;

    public boolean isRecruiting() {
        return this == SessionRecruitment.RECRUITING;
    }
}
