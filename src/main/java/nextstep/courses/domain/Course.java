package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private Long id;

    private final String title;

    private final Long creatorId;

    private final int cohort;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Session> sessions = new ArrayList<>();

    public Course(String title, Long creatorId, int cohort) {
        this(0L, title, creatorId, cohort, LocalDateTime.now(), LocalDateTime.now());
    }

    public Course(Long id, String title, Long creatorId, int cohort, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.cohort = cohort;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public int getCohort() {
        return cohort;
    }
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creatorId=" + creatorId +
                ", cohort=" + cohort +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
