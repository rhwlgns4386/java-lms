package nextstep.courses.domain.session;

import nextstep.courses.type.SessionState;

import java.time.LocalDateTime;

public class PaidSession extends Session {

    protected PaidSession(String name, CoverImage coverImage, SessionState sessionState, int maxEnrollment,
                          long sessionFee, LocalDateTime startDate, LocalDateTime endDate) {
        super(name, coverImage, maxEnrollment, sessionState, sessionFee, startDate, endDate);
    }

    protected PaidSession(Long id, String name, CoverImage coverImage, SessionState sessionState, int maxEnrollment,
                          long sessionFee, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, name, coverImage, maxEnrollment, sessionState, sessionFee, startDate, endDate);
    }

    @Override
    protected void checkRegister() {
        if (enrollment >= maxEnrollment) {
            throw new IllegalStateException("강의 수강 가능 인원이 초과되었습니다");
        }
    }
}
