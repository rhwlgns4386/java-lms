package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public class PaidSession extends DefaultSession {
    public PaidSession(Status status, SessionStatus status2, Period period, CoverImage coverImage, Money courseFee, Capacity capacity) {
        this(0L, status, status2, period, List.of(coverImage), courseFee, capacity);
    }

    public PaidSession(Status status, Period period, CoverImage coverImage, Money courseFee, Capacity capacity) {
        this(0L, status, period, coverImage, courseFee, capacity);
    }

    public PaidSession(Long id, Status status, Period period, CoverImage coverImage, Money courseFee, Capacity capacity) {
        super(id, status, period, coverImage, courseFee, capacity);
    }

    public PaidSession(Status status, Period period, List<CoverImage> coverImages, Money courseFee, Capacity capacity) {
        this(0L, status, period, coverImages, courseFee, capacity);
    }

    public PaidSession(Long id, Status status, Period period, List<CoverImage> coverImages, Money courseFee, Capacity capacity) {
        super(id, status, period, coverImages, courseFee, capacity);
    }

    public PaidSession(Long id, Status status, SessionStatus status2, Period period, List<CoverImage> coverImages, Money courseFee, Capacity capacity) {
        super(id, status, status2, period, coverImages, courseFee, capacity);
    }

    @Override
    protected void validate(NsUser student, Payment payment) {
        validateCapacity();
        validatePayment(payment);
    }

    @Override
    protected void doRegister(NsUser student, Payment payment) {
        capacity = capacity.register(student);
    }

    private void validateCapacity() {
        if (capacity.isFull()) {
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
