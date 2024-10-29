package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.util.List;

public class FreeSession extends Session {

    public FreeSession(Long sessionId, SessionDate date, SessionImage image, SessionStatus status, List<Long> numOfStudents) {
        super(sessionId, date, image, status, numOfStudents);
    }

    @Override
    public void enroll(Payment payment) {
        if (!payment.isFree()) {
            throw new IllegalStateException("무료강의의 수강료는 지불되어선 안됩니다.");
        }
        super.enroll(payment);
    }
}
