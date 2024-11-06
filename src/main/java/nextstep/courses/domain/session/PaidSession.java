package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public class PaidSession extends DefaultSession {
    public PaidSession(SessionProgress progress, SessionRecruitmentStatus recruitmentStatus, Period period, List<CoverImage> coverImages, Money courseFee, int maxStudents) {
        super(0L, period, coverImages, courseFee, maxStudents, SessionType.PAID, progress, recruitmentStatus);
    }

    public PaidSession(Long id, SessionProgress progress, SessionRecruitmentStatus recruitmentStatus, Period period, List<CoverImage> images, Money courseFee, int maxStudents) {
        super(id, period, images, courseFee, maxStudents, SessionType.PAID, progress, recruitmentStatus);
    }


    public PaidSession(Long id, SessionProgress progress, SessionRecruitmentStatus recruitment, Period period, List<CoverImage> images, Money courseFee, int maxStudents, List<SessionRegistration> registrations) {
        super(id, period, images, courseFee, maxStudents, SessionType.PAID, registrations, progress, recruitment);
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
