package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Course {
    private Long id;

    private String title;

    private Long creatorId;

    private int cardinalNumber;

    private List<Session> sessions;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Course() {
    }

    public Course(String title, Long creatorId, int cardinalNumber) {
        this(0L, title, creatorId, cardinalNumber, LocalDateTime.now(), null);
    }

    public Course(Long id, String title, Long creatorId, int cardinalNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.cardinalNumber = cardinalNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public int getCardinalNumber() {
        return cardinalNumber;
    }

    public Long getCreatorId() {
        return creatorId;
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
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
