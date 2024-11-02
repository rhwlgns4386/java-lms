package nextstep.courses.service;

import nextstep.courses.domain.session.Session;
import nextstep.courses.entity.SessionEntity;
import nextstep.courses.infrastructure.session.SessionRepository;
import nextstep.payments.domain.Payment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void register(Long sessionId, Payment payment) {
        Session foundSession = Optional.ofNullable(sessionRepository.findById(sessionId))
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다"))
                .toDomain();
        foundSession.register(payment);
        sessionRepository.save(SessionEntity.from(foundSession), Session.NOT_ASSIGNED);
    }
}
