package nextstep.courses.domain;

import java.util.Set;

public interface EnrollmentStudentRepository {
    Set<EnrollmentStudent> findBySessionId(long sessionId);

    void update(Enrollments session);

    Set<EnrollmentStudent> findByPendingStudentSessionId(long sessionId);
}
