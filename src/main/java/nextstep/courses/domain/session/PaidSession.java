package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public class PaidSession extends DefaultSession {
    public PaidSession(SessionStatus status, Period period, List<CoverImage> coverImages, Money courseFee, int maxStudents) {
        super(0L, status, period, coverImages, courseFee, maxStudents, SessionType.PAID);
    }

    public PaidSession(Long id, SessionStatus sessionStatus, Period period, List<CoverImage> images, Money courseFee, int maxStudents) {
        super(id, sessionStatus, period, images, courseFee, maxStudents, SessionType.PAID);
    }

    public PaidSession(Long id, SessionStatus sessionStatus, Period period, List<CoverImage> images, Money courseFee, int maxStudents, List<SessionRegistration> registrations) {
        super(id, sessionStatus, period, images, courseFee, maxStudents, SessionType.PAID, registrations);
    }

    @Override
    protected void validate(NsUser student, Payment payment) {
        validateCapacity();
        validatePayment(payment);
    }

    @Override
    protected void doRegister(NsUser user, Payment payment) {
        registrations.add(new SessionRegistration(id, user.getId()));
    }

    private void validateCapacity() {
        if (registrations.size() >= maxStudents) {
            throw new IllegalArgumentException("수강 인원이 꽉 찼습니다.");
        }
    }

    private void validatePayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("결제 정보가 없습니다.");
        }
        if (courseFee.isDifferent(new Money(payment))) {
            throw new IllegalArgumentException("결제 금액이 수강료와 일치하지 않습니다");
        }
    }
}
