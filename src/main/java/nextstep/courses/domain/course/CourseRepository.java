package nextstep.courses.domain.course;

import java.util.Optional;

public interface CourseRepository {
    long save(Course course);

    Optional<Course> findById(Long id);
}
