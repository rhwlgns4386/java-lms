package nextstep.courses.domain.port;

import nextstep.courses.domain.enroll.EnrollUserInfo;

public interface EnrollUserInfoRepository {

    int save(EnrollUserInfo enrollUserInfo);

    EnrollUserInfo findBySessionIdAndNsUserId(Long sessionId, Long userId);

}
