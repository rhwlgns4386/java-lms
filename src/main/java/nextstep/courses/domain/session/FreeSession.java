package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class FreeSession extends DefaultSession {

    public FreeSession(Status status, Period period, CoverImage coverImage) {
        this(0L, status, period, coverImage, new Money(0), new Capacity(Integer.MAX_VALUE));
    }

    public FreeSession(Long id, Status status, Period period, CoverImage coverImage, Money courseFee, Capacity capacity) {
        super(id, status, period, coverImage, courseFee, capacity);
    }

    //무료 세선은 수강신청 제한이 없음
    @Override
    protected void validate(NsUser student, Payment payment) {
        validateSessionStatus();
    }

    @Override
    protected void doRegister(NsUser user, Payment payment) {
        capacity = capacity.register(user);
    }

    public void register(NsUser student) {
        register(student, null);
    }

}
