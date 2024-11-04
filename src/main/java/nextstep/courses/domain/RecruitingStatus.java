package nextstep.courses.domain;

public enum RecruitingStatus {
     RECRUITING, NON_RECRUITING;

    public boolean canEnroll() {
        return this.equals(RECRUITING);
    }
}
