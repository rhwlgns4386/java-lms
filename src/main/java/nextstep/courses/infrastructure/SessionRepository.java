package nextstep.courses.infrastructure;

import nextstep.courses.domain.Session;

public interface SessionRepository {
    int save(Session session);
    int saveNew(Session session);

    Session findById(Long id);
    Session findByIdNew(Long id);
}
