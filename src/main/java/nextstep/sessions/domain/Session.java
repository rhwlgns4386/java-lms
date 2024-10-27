package nextstep.sessions.domain;


import nextstep.courses.domain.Course;
import nextstep.registration.domain.SessionRegistrationInfo;
import nextstep.sessions.exception.CannotEnrollException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Session {
    private Long id;

    private SessionImage image;

    private Period period;

    private SessionType type;

    private EnrolledUserInfos enrolledUserInfos;

    private SessionStatus status;

    private Course course;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Session() {
    }

    public Session(Long id, SessionImage image, Period period, SessionType type) {
        this.id = id;
        this.image = image;
        this.period = period;
        this.type = type;
        this.enrolledUserInfos = new EnrolledUserInfos();
        this.status = SessionStatus.PREPARING;
        this.createdAt = LocalDateTime.now();
    }

    public void enroll(NsUser user, Payment payment) {
        validateEnrollment(user);
        validatePayment(payment);
        enrolledUserInfos.add(new SessionRegistrationInfo(this, user));
    }

    private void validateEnrollment(NsUser user) {
        if (!status.isRecruiting()) {
            throw new CannotEnrollException("모집중인 강의가 아닙니다.");
        }
        if (enrolledUserInfos.isAlreadyEnrolled(user)) {
            throw new CannotEnrollException("이미 수강 중인 사용자입니다.");
        }
        if (type.isOverEnrollment(enrolledUserInfos.size())) {
            throw new CannotEnrollException("수강 인원이 초과되었습니다.");
        }
    }

    private void validatePayment(Payment payment) {
        if (!payment.isComplete(type.getTuition())) {
            throw new CannotEnrollException("결제가 완료되지 않았습니다.");
        }
    }

    public void updateToRecruiting() {
        if (status != SessionStatus.PREPARING) {
            throw new IllegalArgumentException("모집중인 강의가 아닙니다.");
        }
        status = SessionStatus.RECRUITING;
    }

    public int getEnrolledUserInfosSize() {
        return enrolledUserInfos.size();
    }
}
