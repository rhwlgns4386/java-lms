package nextstep.courses.domain;

import nextstep.qna.domain.Question;

import java.util.Optional;

public interface SessionRepository {

    Optional<Session> findById(Long id);

    void save(Session session);

}
