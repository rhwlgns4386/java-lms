package nextstep.global.domain;

import java.time.LocalDateTime;

public abstract class BaseEntity {
    protected Long id;

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

    protected BaseEntity() {
    }

    protected BaseEntity(Long id, LocalDateTime createdAt) {
        this(id, createdAt, null);
    }

    protected BaseEntity(Long id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
