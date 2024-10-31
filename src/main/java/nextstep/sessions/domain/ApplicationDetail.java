package nextstep.sessions.domain;

import java.time.LocalDateTime;

public class ApplicationDetail {
    private Long sessionId;
    private Long nsUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ApplicationDetail(Long sessionId, Long nsUserId) {
        this(sessionId, nsUserId,  LocalDateTime.now(), null);
    }

    public ApplicationDetail(Long sessionId, Long nsUserId,  LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (sessionId == null || nsUserId == null) {
            throw new RuntimeException("수강신청내역에 강의식별자/고객식별자는 필수 입력입니다");
        }
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ApplicationDetail ofNewInstance(Long sessionId, Long nsUserId) {
        return new ApplicationDetail(sessionId, nsUserId);
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isPresent(Long inputSessionId, Long inputNsUserId) {
        return (this.sessionId.equals(sessionId)
                && this.nsUserId.equals(inputNsUserId));
    }
}
