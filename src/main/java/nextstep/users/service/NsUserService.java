package nextstep.users.service;

import nextstep.registration.domain.SessionRegistrationInfo;
import nextstep.registration.domain.SessionRegistrationInfoRepository;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NsUserService {

    private final UserRepository userRepository;
    private final SessionRegistrationInfoRepository sessionRegistrationInfoRepository;

    public NsUserService(UserRepository userRepository, SessionRegistrationInfoRepository sessionRegistrationInfoRepository) {
        this.userRepository = userRepository;
        this.sessionRegistrationInfoRepository = sessionRegistrationInfoRepository;
    }

    @Transactional
    public void approveSessionRegistration(String creatorUserId, Long sessionId, Long studentId) {
        NsUser creator = userRepository.findByUserId(creatorUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자를 찾을 수 없습니다."));
        SessionRegistrationInfo info = sessionRegistrationInfoRepository.findBySessionIdAndUserId(sessionId, studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 세션 등록 정보를 찾을 수 없습니다."));

        creator.approveSessionStudent(info);
    }

    @Transactional
    public void rejectSessionRegistration(String creatorUserId, Long sessionId, Long studentId) {
        NsUser creator = userRepository.findByUserId(creatorUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 사용자를 찾을 수 없습니다."));
        SessionRegistrationInfo info = sessionRegistrationInfoRepository.findBySessionIdAndUserId(sessionId, studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 세션 등록 정보를 찾을 수 없습니다."));

        creator.rejectSessionStudent(info);
    }
}
