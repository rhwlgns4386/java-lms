package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public class FreeSession extends DefaultSession {
    public FreeSession(SessionProgress progress, SessionRecruitmentStatus recruitmentStatus, Period period, CoverImage coverImage) {
        super(0L, period, List.of(coverImage), new Money(0L), Integer.MAX_VALUE, SessionType.FREE, progress, recruitmentStatus);
    }

    public FreeSession(Long id,SessionProgress progress, SessionRecruitmentStatus recruitmentStatus, Period period, List<CoverImage> images, int maxStudents, List<SessionRegistration> registrations) {
        super(id, period, images, new Money(0L), maxStudents, SessionType.FREE, registrations, progress, recruitmentStatus);
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
