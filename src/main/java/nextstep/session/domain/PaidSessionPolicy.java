package nextstep.session.domain;

import nextstep.enrollment.domain.Enrollment;

public class PaidSessionPolicy implements SessionPolicy {

    public static final int MIN_CAPACITY = 1;

    private final int maxCapacity;
    private final Long sessionFee;

    public PaidSessionPolicy(int maxCapacity, Long sessionFee) {
        if (maxCapacity < MIN_CAPACITY) {
            throw new IllegalArgumentException("최대 수강 인원은 1명 이상이어야 합니다.");
        }
        this.sessionFee = sessionFee;
        this.maxCapacity = maxCapacity;
    }

    @Override
    public void validatePolicy(int enrollStudentCount, Enrollment enrollment) {
        if (enrollStudentCount >= maxCapacity) {
            throw new IllegalArgumentException("정원을 초과했습니다.");
        }
        if (!enrollment.getPaymentAmount().equals(sessionFee)) {
            throw new IllegalArgumentException("결제금액이 수강료와 일치하지 않습니다.");
        }
    }
}
