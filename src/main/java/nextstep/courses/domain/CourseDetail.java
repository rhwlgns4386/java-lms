package nextstep.courses.domain;

public class CourseDetail {
    private final String title;
    private final Long creatorId;
    private final int cohort;

    public CourseDetail(String title, Long creatorId, int cohort) {
        this.title = title;
        this.creatorId = creatorId;
        this.cohort = cohort;
    }

    public String getTitle() {
        return title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public int getCohort() {
        return cohort;
    }
}
