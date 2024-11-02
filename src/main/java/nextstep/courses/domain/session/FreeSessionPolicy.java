package nextstep.courses.domain.session;

public class FreeSessionPolicy implements SessionPolicy {

    @Override
    public void validatePolicy(int enrollStudentCount, Long paymentAmount) {
        // 무료강의는 정책이 존재하지 않음
    }

    @Override
    public SessionPaymentType getSessionPaymentType() {
        return SessionPaymentType.FREE;
    }
}
