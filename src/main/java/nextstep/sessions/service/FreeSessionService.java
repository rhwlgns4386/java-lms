package nextstep.sessions.service;

import nextstep.qna.NotFoundException;
import nextstep.sessions.domain.FreeSession;
import nextstep.sessions.domain.SessionRepository;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service("freeSessionService")
public class FreeSessionService extends SessionService {
    @Resource(name = "freeSessionRepository")
    private SessionRepository<FreeSession> freeSessionRepository;

    @Transactional
    public void registerSession(NsUser loginUser, Long sessionId) {
        FreeSession session = freeSessionRepository.findById(sessionId).orElseThrow(NotFoundException::new);

        session.registerSession(loginUser, LocalDateTime.now());
    }

}
