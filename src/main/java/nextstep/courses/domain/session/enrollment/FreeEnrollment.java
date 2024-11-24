package nextstep.courses.domain.session.enrollment;

import nextstep.courses.CannotApplyException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public class FreeEnrollment implements Enrollment {
    private final Status status;
    private final EnrollmentStatus enrollmentStatus;
    private final Students students;

    public FreeEnrollment(Status status, EnrollmentStatus enrollmentStatus, Students students) {
        this.status = status;
        this.enrollmentStatus = enrollmentStatus;
        this.students = students;
    }

    @Override
    public void enroll(NsUser student, Payment payment) {
        if (!status.isProgress() || enrollmentStatus.isImPossible()) {
            throw new CannotApplyException("현재 모집중인 강의가 아닙니다.");
        }

        if (students.alreadyEnrolled(student)) {
            throw new CannotApplyException("이미 수강신청이 완료된 학생입니다.");
        }

        students.enroll(student);
    }

    @Override
    public void approve(List<Long> userIdList) {
        students.approve(userIdList);
    }
}
