package nextstep.courses.domain.course;

import java.util.List;

public interface CourseSessionRepository {
    void addSessionToCourse(Long courseId, Long sessionId);

    List<Long> findSessionIdsByCourseId(Long courseId);
}
