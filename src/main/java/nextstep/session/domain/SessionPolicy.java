package nextstep.session.domain;

import nextstep.enrollment.domain.Enrollment;

public interface SessionPolicy {

    void validatePolicy(Enrollment enrollment);

    boolean isMatch(SessionType sessionType);

}
