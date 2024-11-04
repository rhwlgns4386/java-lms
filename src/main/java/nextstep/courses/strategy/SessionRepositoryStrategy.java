package nextstep.courses.strategy;

import nextstep.courses.domain.Session;

public interface SessionRepositoryStrategy {
    int saveRegisterSession(Session session);
}
