package nextstep.courses.domain;

public abstract class DefaultCourse {
    protected final CourseStatus status;
    protected final Course2Period period;

    protected DefaultCourse(CourseStatus status, Course2Period period) {
        this.status = status;
        this.period = period;
    }

    protected abstract void register(Money amount);

    protected void validateRegisterStatus() {
        if(status.isOpen()){
            return;
        }
        throw new IllegalArgumentException("강의 상태가 모집 중일때만 수강신청이 가능합니다.");
    }
}
