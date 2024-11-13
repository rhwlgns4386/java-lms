package nextstep.enrollment.domain;

import java.util.Optional;

public interface EnrollmentRepository {

    Enrollment save(Enrollment enrollment);

    Optional<Enrollment> findById(Long id);

    // 강의의 승인된 수강신청 개수를 구한다.
    int countApprovedEnrollmentsBySessionId(Long SessionId);

}
