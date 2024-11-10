package nextstep.session.domain;

import org.springframework.stereotype.Component;

import nextstep.enrollment.domain.Enrollment;

@Component
public class FreeSessionPolicy implements SessionPolicy {

    @Override
    public void validatePolicy(Enrollment enrollment) {
        // 무료강의는 정책이 존재하지 않음
    }

    @Override
    public boolean isMatch(SessionType sessionType) {
        return sessionType == SessionType.FREE;
    }
}
