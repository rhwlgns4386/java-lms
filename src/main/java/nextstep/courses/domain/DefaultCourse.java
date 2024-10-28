package nextstep.courses.domain;

public abstract class DefaultCourse {
    protected final CourseStatus status;
    protected final Course2Period period;

    protected DefaultCourse(CourseStatus status, Course2Period period) {
        this.status = status;
        this.period = period;
    }

    protected abstract boolean canEnroll();
}
