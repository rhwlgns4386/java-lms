package nextstep.courses.service;

import nextstep.courses.domain.session.Session;
import nextstep.courses.entity.SessionEntity;
import nextstep.courses.infrastructure.session.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserService userService;

    public SessionService(SessionRepository sessionRepository, UserService userService) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
    }

    public void register(Long sessionId, Payment payment) {
        Session foundSession = Optional.ofNullable(sessionRepository.findById(sessionId))
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다"))
                .toDomain();
        foundSession.register(payment);
        sessionRepository.save(SessionEntity.from(foundSession), Session.NOT_ASSIGNED);
    }

    public void apply(String userId, Long sessionId, Payment payment) {
        Session foundSession = Optional.ofNullable(sessionRepository.findById(sessionId))
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다"))
                .toDomain();
        foundSession.apply(userService.getUser(userId), payment);
        sessionRepository.save(SessionEntity.from(foundSession), Session.NOT_ASSIGNED);
    }

    public void register(String userId, Long sessionId) {
        Session foundSession = Optional.ofNullable(sessionRepository.findById(sessionId))
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다"))
                .toDomain();
        foundSession.register(userService.getUser(userId));
        sessionRepository.save(SessionEntity.from(foundSession), Session.NOT_ASSIGNED);
    }

    public void reject(String userId, Long sessionId) {
        Session foundSession = Optional.ofNullable(sessionRepository.findById(sessionId))
                .orElseThrow(() -> new IllegalArgumentException("해당 강의를 찾을 수 없습니다"))
                .toDomain();
        foundSession.reject(userService.getUser(userId));
        sessionRepository.save(SessionEntity.from(foundSession), Session.NOT_ASSIGNED);
    }
}
