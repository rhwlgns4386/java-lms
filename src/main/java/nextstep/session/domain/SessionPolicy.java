package nextstep.session.domain;

import nextstep.enrollment.domain.Enrollment;

public interface SessionPolicy {

    void validatePolicy(int enrollStudentCount, Enrollment enrollment);

}
