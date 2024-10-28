package nextstep.courses.domain;

public class PaidCourse extends DefaultCourse {
    private final Money courseFee;
    private CourseCapacity capacity;

    public PaidCourse(CourseStatus courseStatus, Course2Period period, CourseCapacity capacity, Money courseFee) {
        super(courseStatus, period);
        this.capacity = capacity;
        this.courseFee = courseFee;
    }

    @Override
    protected void register(Money amount) {
        validateRegisterStatus();
        validateCapacity();
        validatePayment(amount);
        this.capacity = capacity.increase();
    }

    private void validateCapacity() {
        if (capacity.isFull()) {
            throw new IllegalArgumentException("수강 인원이 꽉 찼습니다.");
        }
    }

    private void validatePayment(Money amount) {
        if (courseFee.isDifferent(amount)) {
            throw new IllegalArgumentException("결제 금액이 수강료와 일치하지 않습니다");
        }
    }
}
