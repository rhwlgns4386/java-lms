package nextstep.courses.infrastructure;

import java.util.List;

public interface CourseSessionRepository {
    int save(Long courseId, List<Long> sessionIds);
    List<Long> findByCourseId(Long courseId);
}
