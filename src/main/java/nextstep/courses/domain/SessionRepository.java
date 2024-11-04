package nextstep.courses.domain;

public interface SessionRepository<T extends Session> {
    Long saveBaseSession(T session, Long courseId);

    T findById(Long id);
}
