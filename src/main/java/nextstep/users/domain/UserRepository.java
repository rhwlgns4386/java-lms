package nextstep.users.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<NsUser> findById(long id);

    Optional<NsUser> findByUserId(String userId);

    List<NsUser> findBySessionId(long sessionId);
}
