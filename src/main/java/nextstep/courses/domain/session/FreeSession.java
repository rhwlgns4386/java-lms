package nextstep.courses.domain.session;

import nextstep.courses.type.SessionState;

import java.time.LocalDateTime;

public class FreeSession extends Session {

    static final int INFINITE_ENROLLMENT = -1;
    static final int FREE_SESSION_FEE = 0;

    protected FreeSession(String name, CoverImage coverImage, SessionState sessionState,
                          LocalDateTime startDate, LocalDateTime endDate) {
        super(name, coverImage, INFINITE_ENROLLMENT, sessionState, FREE_SESSION_FEE, startDate, endDate);
    }

    protected FreeSession(Long id, String name, CoverImage coverImage, SessionState sessionState,
                          LocalDateTime startDate, LocalDateTime endDate) {
        super(id, name, coverImage, INFINITE_ENROLLMENT, sessionState, FREE_SESSION_FEE, startDate, endDate);
    }

    @Override
    protected void checkRegister() {
        System.out.println("무료 강의 수강 예약 완료");
    }
}
