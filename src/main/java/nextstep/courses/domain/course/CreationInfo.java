package nextstep.courses.domain.course;

import java.time.LocalDateTime;

public class CreationInfo {

    private Long creatorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public CreationInfo(Long creatorId) {
        this(creatorId, LocalDateTime.now(), null);
    }

    public CreationInfo(Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update() {
        updatedAt = LocalDateTime.now();
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "CreationInfo{" +
                "creatorId=" + creatorId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
