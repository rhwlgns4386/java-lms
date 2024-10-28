package nextstep.courses.domain;

public class Course2 {
    private final CourseStatus status;
    private final Course2Period period;

    public Course2(CourseStatus courseStatus, Course2Period period) {
        this.status = courseStatus;
        this.period = period;
    }

    public void register() {
        if (status.isOpen()) {
            return;
        }
        throw new IllegalArgumentException("강의상태가 모집 중일때만 수강신청이 가능합니다.");
    }

}
