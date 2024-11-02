package nextstep.courses.infrastructure.course;

import nextstep.courses.entity.CourseEntity;

public interface CourseRepository {

    int save(CourseEntity course);

    CourseEntity findById(Long id);
}
