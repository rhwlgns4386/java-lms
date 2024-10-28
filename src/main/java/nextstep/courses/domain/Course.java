package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Course {
    private Long id;

    private String title;

    private int generation;

    private Sessions sessions;

    private Long creatorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Course() {
    }

    public Course(String title, Long creatorId) {
        this(0L, title, 0, creatorId, LocalDateTime.now(), null, new Sessions());
    }

    public Course(String title, Long creatorId, int generation, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(0L, title, generation, creatorId, createdAt, updatedAt, new Sessions());
    }

    public Course(Long id, String title, int generation, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt, Sessions sessions) {
        this.id = id;
        this.title = title;
        this.generation = generation;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sessions = sessions;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creatorId=" + creatorId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Course))
            return false;
        Course course = (Course)o;
        return generation == course.generation && Objects.equals(id, course.id) && Objects.equals(getTitle(), course.getTitle())
            && Objects.equals(sessions, course.sessions) && Objects.equals(getCreatorId(), course.getCreatorId())
            && Objects.equals(getCreatedAt(), course.getCreatedAt()) && Objects.equals(getUpdatedAt(), course.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getTitle(), generation, sessions, getCreatorId(), getCreatedAt(), getUpdatedAt());
    }
}
