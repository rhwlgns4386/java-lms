package nextstep.courses.domain;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CourseRepository {
    int save(Course course);

    Optional<Course> findById(Long id);
}
