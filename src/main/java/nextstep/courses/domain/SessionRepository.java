package nextstep.courses.domain;

import nextstep.courses.domain.session.Session;

public interface SessionRepository {
    Long save(Session session);

    Session findById(Long id);
}
