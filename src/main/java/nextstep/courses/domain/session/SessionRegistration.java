package nextstep.courses.domain.session;

import java.time.LocalDateTime;

public class SessionRegistration {
    private final Long sessionId;
    private final Long userId;
    private final LocalDateTime registeredAt;
    private SessionRegistrationStatus registrationStatus;
    private StudentApprovalStatus selectionStatus;

    public SessionRegistration(Long sessionId, Long userId) {
        this(sessionId, userId, LocalDateTime.now(), SessionRegistrationStatus.PENDING, StudentApprovalStatus.PENDING);
    }

    public SessionRegistration(Long sessionId, Long userId, LocalDateTime registeredAt, SessionRegistrationStatus registrationStatus, StudentApprovalStatus selectionStatus) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.registeredAt = registeredAt;
        this.registrationStatus = registrationStatus;
        this.selectionStatus = selectionStatus;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void approveStudent() {
        validateSelectionChange();
        this.selectionStatus = StudentApprovalStatus.APPROVED;
    }

    public void rejectStudent() {
        validateSelectionChange();
        this.selectionStatus = StudentApprovalStatus.REJECTED;
    }

    public void approveRegistration() {
        validateApproval();
        this.registrationStatus = SessionRegistrationStatus.APPROVED;
    }

    public void cancelRegistration() {
        validateCancellation();
        this.registrationStatus = SessionRegistrationStatus.CANCEL;
    }

    private void validateSelectionChange() {
        if (selectionStatus != StudentApprovalStatus.PENDING) {
            throw new IllegalStateException("이미 선발 여부가 결정되었습니다.");
        }
    }

    private void validateApproval() {
        if (selectionStatus != StudentApprovalStatus.APPROVED) {
            throw new IllegalStateException("선발되지 않은 학생은 수강 승인할 수 없습니다.");
        }
        if (registrationStatus != SessionRegistrationStatus.PENDING) {
            throw new IllegalStateException("이미 처리된 수강신청입니다.");
        }
    }

    private void validateCancellation() {
        if (selectionStatus != StudentApprovalStatus.REJECTED) {
            throw new IllegalStateException("선발된 학생의 수강신청은 취소할 수 없습니다.");
        }
        if (registrationStatus != SessionRegistrationStatus.PENDING) {
            throw new IllegalStateException("이미 처리된 수강신청입니다.");
        }
    }

    public SessionRegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public StudentApprovalStatus getSelectionStatus() {
        return selectionStatus;
    }

}
