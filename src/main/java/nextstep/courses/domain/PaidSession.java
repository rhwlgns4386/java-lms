package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.util.List;

public class PaidSession extends Session {
    private final int maxNumOfStudents;
    private final int sessionFee;

    public PaidSession(Long sessionId, SessionDate date, SessionImage image, SessionStatus status, List<Long> numOfStudents, int maxNumOfStudents, int sessionFee) {
        super(sessionId, date, image, status, numOfStudents);
        if (maxNumOfStudents < students.size()) {
            throw new IllegalArgumentException("수강 정원이 초과됐습니다.");
        }
        this.maxNumOfStudents = maxNumOfStudents;
        this.sessionFee = sessionFee;
    }

    @Override
    public void enroll(Payment payment) {
        if (maxNumOfStudents <= students.size()) {
            throw new IllegalStateException("수강 정원이 초과됐습니다.");
        }
        super.enroll(payment);
    }

}
