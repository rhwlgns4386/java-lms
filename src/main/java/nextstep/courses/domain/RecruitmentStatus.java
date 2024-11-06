package nextstep.courses.domain;

public enum RecruitmentStatus {
    RECRUITING("모집중"),
    NOT_RECRUITING("비모집중");

    private final String description;

    RecruitmentStatus(String description) {
        this.description = description;
    }

    public boolean canEnroll() {
        return this == RECRUITING;
    }

}
