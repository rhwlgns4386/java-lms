package nextstep.sessions.domain;

public enum RecruitmentStatus {
    RECRUITING("모집중"),
    NOT_RECRUITING("비모집중");

    private String state;

    RecruitmentStatus(String state) {
        this.state = state;
    }

    public boolean isRecruiting() {
        return this == RECRUITING;
    }

}
