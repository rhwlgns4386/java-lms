package nextstep.courses.domain;

import nextstep.global.domain.BaseEntity;
import nextstep.sessions.domain.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course extends BaseEntity {
    private String title;

    private Long creatorId;

    private Integer cohort;

    private List<Session> sessions;

    public Course() {
    }

    public Course(String title, Long creatorId) {
        this(0L, title, creatorId, 1, LocalDateTime.now(), null);
    }

    public Course(Long id, String title, Long creatorId, Integer cohort, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.title = title;
        this.creatorId = creatorId;
        this.cohort = cohort;
        this.sessions = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Integer getCohort() {
        return cohort;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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
