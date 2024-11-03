package nextstep.courses.domain.session;

public interface SessionStatusRepository {
    void save(Long sessionId, SessionStatus sessionStatus);

    SessionStatus findBySessionId(Long sessionId);
}
