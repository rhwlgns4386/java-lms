package nextstep.courses.domain;

import nextstep.courses.domain.session.DefaultSession;
import nextstep.courses.domain.session.FreeSession;
import nextstep.courses.domain.session.PaidSession;

public interface SessionRepository {
    Long saveFreeSession(FreeSession session);
    Long savePaidSession(PaidSession session);
    DefaultSession findById(Long id);
}
