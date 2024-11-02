package nextstep.courses.domain.session;

public interface SessionPolicy {

    void validatePolicy(int enrollStudentCount, Long paymentAmount);

    SessionPaymentType getSessionPaymentType();

}
