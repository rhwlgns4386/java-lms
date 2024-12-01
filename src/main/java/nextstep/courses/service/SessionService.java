package nextstep.courses.service;

import nextstep.courses.EntityNotFoundException;
import nextstep.courses.domain.Charge;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SessionService {

    private SessionRepository sessionRepository;

    @Transactional
    public void enroll(long sessionId, int fee, NsUser user) {
        Session session = findById(sessionId);
        session.enrollment(new Charge(fee), user);
        sessionRepository.update(session);
    }

    @Transactional(readOnly = true)
    public Session findById(long sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException(Session.class));
    }

}
