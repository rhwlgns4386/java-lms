package nextstep.sessions.service;

import nextstep.qna.NotFoundException;
import nextstep.sessions.domain.FreeSession;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service("freeSessionService")
public class FreeSessionService extends SessionService {
    @Transactional
    public void registerSession(NsUser loginUser, Long sessionId) {
        FreeSession session = sessionRepository.findFreeSessionById(sessionId).orElseThrow(NotFoundException::new);

        session.registerSession(loginUser, LocalDateTime.now());
    }

}
