package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public class FreeSession extends DefaultSession {
    public FreeSession(SessionStatus status, Period period, CoverImage coverImage) {
        super(0L, status, period, List.of(coverImage), new Money(0L), Integer.MAX_VALUE, SessionType.FREE);
    }

    public FreeSession(Long id, SessionStatus sessionStatus, Period period, List<CoverImage> images, int maxStudents) {
        super(id, sessionStatus, period, images, new Money(0L), maxStudents, SessionType.FREE);
    }

    public FreeSession(Long id, SessionStatus sessionStatus, Period period, List<CoverImage> images, int maxStudents, List<SessionRegistration> registrations) {
        super(id, sessionStatus, period, images, new Money(0L), maxStudents, SessionType.FREE, registrations);
    }

    @Override
    protected void validate(NsUser student, Payment payment) {
        //무료 세선은 수강신청 제한이 없음
    }

    @Override
    protected void doRegister(NsUser user, Payment payment) {
        registrations.add(new SessionRegistration(id, user.getId()));
    }

    public void register(NsUser student) {
        register(student, null);
    }

}
