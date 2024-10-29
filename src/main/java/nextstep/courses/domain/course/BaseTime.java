package nextstep.courses.domain.course;

import java.time.LocalDateTime;

public class BaseTime {
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BaseTime() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    public BaseTime(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updatedAt(LocalDateTime updatedAt) {
        if(updatedAt == null) {
            throw new IllegalArgumentException("업데이트 시간이 없습니다.");
        }
        if(updatedAt.isBefore(this.createdAt)) {
            throw new IllegalArgumentException("생성 시간 이전으로 업데이트 시간을 바꿀 수 없습니다.");
        }
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
