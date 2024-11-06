package nextstep.sessions.domain;

import java.time.LocalDateTime;

public class ApplicationDetail {
    private Long sessionId;
    private Long nsUserId;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ApplicationDetail(Long sessionId, Long nsUserId) {

        this(sessionId, nsUserId,  LocalDateTime.now(), null);
    }

    public ApplicationDetail(Long sessionId, Long nsUserId,  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(sessionId, nsUserId, new ApplicationStatus(ApplicationStatusType.APPLY), createdAt, updatedAt);
    }

    public ApplicationDetail(Long sessionId, Long nsUserId, ApplicationStatus status,  LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (status == null) {
            throw new RuntimeException("수강 상태가 누락 되었습니다");
        }
        if (sessionId == null || nsUserId == null) {
            throw new RuntimeException("수강신청내역에 강의식별자/고객식별자는 필수 입력입니다");
        }
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ApplicationDetail ofNewInstance(Long sessionId, Long nsUserId) {
        return new ApplicationDetail(sessionId, nsUserId);
    }

    public void approve() {
        this.status = status.approve();
    }


    public void cancel() {
        this.status = status.cancel();
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isPresent(Long inputSessionId, Long inputNsUserId) {
        return (this.sessionId.equals(sessionId)
                && this.nsUserId.equals(inputNsUserId));
    }

    public boolean isValidStatus() {
        return status.isValidStatusToAttend();
    }

}
