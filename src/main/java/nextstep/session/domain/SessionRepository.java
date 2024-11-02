package nextstep.session.domain;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository {
    Optional<Session> findById(Long id);
}
