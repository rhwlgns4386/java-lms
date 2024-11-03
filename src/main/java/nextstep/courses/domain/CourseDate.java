package nextstep.courses.domain;

import java.time.LocalDateTime;

public class CourseDate {
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CourseDate(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
