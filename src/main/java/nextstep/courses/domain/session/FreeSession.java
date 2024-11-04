package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public class FreeSession extends DefaultSession {
    public FreeSession(SessionStatus status, Period period, CoverImage coverImage) {
        super(0L, status, period, List.of(coverImage), new Money(0), new Capacity(Integer.MAX_VALUE));
    }

    public FreeSession(Long id, SessionStatus status, Period period, List<CoverImage> coverImages, Capacity capacity) {
        super(id, status, period, coverImages, new Money(0), capacity);
    }

    @Override
    protected void validate(NsUser student, Payment payment) {
        //무료 세선은 수강신청 제한이 없음
    }

    @Override
    protected void doRegister(NsUser user, Payment payment) {
        capacity = capacity.register(user);
    }

    public void register(NsUser student) {
        register(student, null);
    }

}
