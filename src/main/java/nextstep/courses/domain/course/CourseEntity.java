package nextstep.courses.domain.course;

import java.time.LocalDateTime;

public class CourseEntity {
    private Long id;
    private String title;
    private Long creatorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CourseEntity(Long id, String title, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CourseEntity from(Course course) {
        return new CourseEntity(
                course.getId(),
                course.getCourseTitle(),
                course.getCreatorId(),
                course.getCreatedAt(),
                null
        );
    }

    public Course toCourse() {
        return new Course(id, title, creatorId, createdAt, updatedAt);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
