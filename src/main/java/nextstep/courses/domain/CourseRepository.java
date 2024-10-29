package nextstep.courses.domain;

import nextstep.courses.domain.course.Course;

public interface CourseRepository {
    int save(Course course);

    Course findById(Long id);
}
