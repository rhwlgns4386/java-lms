package nextstep.courses.entity;

import lombok.Getter;
import nextstep.courses.domain.course.Course;

import java.time.LocalDateTime;

@Getter
public class CourseEntity {

    private Long id;
    private String title;
    private Long creatorId;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public CourseEntity(String title, Long creatorId, LocalDateTime createAt) {
        this(null, title, creatorId, createAt, null);
    }

    public CourseEntity(String title, Long creatorId, LocalDateTime createAt, LocalDateTime updatedAt) {
        this(null, title, creatorId, createAt, null);
    }

    public CourseEntity(Long id, String title, Long creatorId, LocalDateTime createAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public Course toDomain() {
        return new Course(id, title, creatorId, createAt, updatedAt);
    }

    public static CourseEntity from(Course course) {
        return new CourseEntity(course.getId(), course.getTitle(), course.getCreatorId(),
                course.getCreatedAt(), course.getUpdatedAt());
    }
}
