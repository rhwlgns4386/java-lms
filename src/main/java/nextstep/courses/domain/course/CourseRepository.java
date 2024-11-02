package nextstep.courses.domain.course;

public interface CourseRepository {
    long save(Course course);

    Course findById(Long id);
}
