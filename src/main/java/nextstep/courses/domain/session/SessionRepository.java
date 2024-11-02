package nextstep.courses.domain.session;

public interface SessionRepository {
    long save(Session session, Long courseId);

    Session findById(Long id);
}
