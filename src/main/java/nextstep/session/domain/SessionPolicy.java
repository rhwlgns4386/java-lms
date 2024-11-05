package nextstep.session.domain;

public interface SessionPolicy {

    void validatePolicy(int enrollStudentCount, Long paymentAmount);

}
