package nextstep.courses.domain.session.enrollment;

import nextstep.courses.CannotApplyException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class PayEnrollment implements Enrollment {

    private final Status status;
    private final Students students;
    private final Price price;

    public PayEnrollment(Status status, Students students, Price price) {
        this.status = status;
        this.students = students;
        this.price = price;
    }

    @Override
    public void enroll(NsUser student, Payment payment) {
        if (!status.isRecruit()) {
            throw new CannotApplyException("현재 모집중인 강의가 아닙니다.");
        }

        if (students.alreadyEnrolled(student)) {
            throw new CannotApplyException("이미 수강신청이 완료된 학생입니다.");
        }

        if (students.isFull()) {
            throw new CannotApplyException("정원이 초과되어 수강 신청이 불가능합니다.");
        }

        price.isValid(payment);

        students.enroll(student);
    }

}
