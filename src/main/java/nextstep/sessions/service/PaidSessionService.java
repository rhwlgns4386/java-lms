package nextstep.sessions.service;

import nextstep.payments.domain.Payment;
import nextstep.qna.NotFoundException;
import nextstep.sessions.domain.PaidSession;
import nextstep.sessions.domain.SessionRepository;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service("paidSessionService")
public class PaidSessionService extends SessionService {
    @Resource(name = "paidSessionRepository")
    private SessionRepository<PaidSession> paidSessionRepository;

    @Transactional
    public void registerSession(NsUser loginUser, Long sessionId, Payment payment) {
        PaidSession session = paidSessionRepository.findById(sessionId).orElseThrow(NotFoundException::new);

        session.registerSession(loginUser, payment, LocalDateTime.now());
    }
}

