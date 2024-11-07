package nextstep.courses.domain.session;

public interface SessionRepository {
    Long saveFreeSession(FreeSession session);

    Long savePaidSession(PaidSession session);

    DefaultSession findById(Long id);
}
