package nextstep.session.domain;

import org.springframework.stereotype.Component;

import nextstep.enrollment.domain.Enrollment;
import nextstep.enrollment.domain.EnrollmentRepository;

@Component
public class PaidSessionPolicy implements SessionPolicy {

    public static final int MIN_CAPACITY = 1;

    private final EnrollmentRepository enrollmentRepository;

    public PaidSessionPolicy(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public void validatePolicy(Enrollment enrollment) {
        Session session = enrollment.getSession();
        if (session.getStudentCapacity() < MIN_CAPACITY) {
            throw new IllegalArgumentException("최대 수강 인원은 1명 이상이어야 합니다.");
        }
        if (enrollmentRepository.countApprovedEnrollmentsBySessionId(session.getId()) >= session.getStudentCapacity()) {
            throw new IllegalArgumentException("정원을 초과했습니다.");
        }
        if (!enrollment.getPaymentAmount().equals(session.getSessionFee())) {
            throw new IllegalArgumentException("결제금액이 수강료와 일치하지 않습니다.");
        }
    }

    @Override
    public boolean isMatch(SessionType sessionType) {
        return sessionType == SessionType.PAID;
    }
}
