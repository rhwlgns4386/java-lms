package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public class FreeSession extends DefaultSession {
    public FreeSession(Status status, SessionStatus status2, Period period, CoverImage coverImage) {
        this(0L, status, status2, period, List.of(coverImage), new Money(0), new Capacity(Integer.MAX_VALUE));
    }

    public FreeSession(Long id, Status status, Period period, CoverImage coverImage, Capacity capacity) {
        this(id, status, period, coverImage, new Money(0), capacity);
    }

    private FreeSession(Long id, Status status, Period period, CoverImage coverImage, Money courseFee, Capacity capacity) {
        super(id, status, period, coverImage, courseFee, capacity);
    }

    public FreeSession(Long id, Status status, Period period, List<CoverImage> coverImages, Capacity capacity) {
        this(id, status, period, coverImages, new Money(0), capacity);
    }

    private FreeSession(Long id, Status status, Period period, List<CoverImage> coverImages, Money courseFee, Capacity capacity) {
        super(id, status, period, coverImages, courseFee, capacity);
    }

    private FreeSession(Long id, Status status, SessionStatus status2, Period period, List<CoverImage> coverImages, Money courseFee, Capacity capacity) {
        super(id, status, status2, period, coverImages, courseFee, capacity);
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
