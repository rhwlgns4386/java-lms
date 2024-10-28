package nextstep.courses.domain;

public class FreeCourse extends DefaultCourse {

    public FreeCourse(CourseStatus status, Course2Period period) {
        super(status,period);
    }

    @Override
    protected boolean canEnroll() {
        return status.isOpen();
    }

}
