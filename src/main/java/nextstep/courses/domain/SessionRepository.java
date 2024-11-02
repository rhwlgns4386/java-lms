package nextstep.courses.domain;

import nextstep.courses.domain.session.Session;

public interface SessionRepository {
    int save(Session session);

    Session findById(Long id);
}
