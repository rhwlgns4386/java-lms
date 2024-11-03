package nextstep.users.service;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public NsUser getUser(String userId) {
        return userRepository
                .findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("수강 신청자에 대한 정보를 찾을 수 없습니다"));
    }
}
