package nextstep.courses.domain.session;

public class RegistrationState {
    private SessionRegistrationStatus registrationStatus;
    private StudentSelectionStatus selectionStatus;

    public RegistrationState() {
        this(SessionRegistrationStatus.PENDING, StudentSelectionStatus.PENDING);
    }

    public RegistrationState(SessionRegistrationStatus registrationStatus, StudentSelectionStatus selectionStatus) {
        this.registrationStatus = registrationStatus;
        this.selectionStatus = selectionStatus;
    }

    public void select() {
        validateSelectionChange();
        this.selectionStatus = StudentSelectionStatus.SELECTED;
    }

    public void reject() {
        validateSelectionChange();
        this.selectionStatus = StudentSelectionStatus.NOT_SELECTED;
    }

    public void approve() {
        validateApproval();
        this.registrationStatus = SessionRegistrationStatus.APPROVED;
    }

    public void cancel() {
        validateCancellation();
        this.registrationStatus = SessionRegistrationStatus.CANCEL;
    }

    private void validateSelectionChange() {
        if (selectionStatus != StudentSelectionStatus.PENDING) {
            throw new IllegalStateException("이미 선발 여부가 결정되었습니다.");
        }
    }

    private void validateApproval() {
        if (selectionStatus != StudentSelectionStatus.SELECTED) {
            throw new IllegalStateException("선발되지 않은 학생은 수강 승인할 수 없습니다.");
        }
        if (registrationStatus != SessionRegistrationStatus.PENDING) {
            throw new IllegalStateException("이미 처리된 수강신청입니다.");
        }
    }

    private void validateCancellation() {
        if (selectionStatus != StudentSelectionStatus.NOT_SELECTED) {
            throw new IllegalStateException("선발된 학생의 수강신청은 취소할 수 없습니다.");
        }
        if (registrationStatus != SessionRegistrationStatus.PENDING) {
            throw new IllegalStateException("이미 처리된 수강신청입니다.");
        }
    }

    public boolean isSelected() {
        return selectionStatus == StudentSelectionStatus.SELECTED;
    }

    public boolean isRejected() {
        return selectionStatus == StudentSelectionStatus.NOT_SELECTED;
    }

    public boolean isPending() {
        return registrationStatus == SessionRegistrationStatus.PENDING;
    }

    public boolean isApproved() {
        return registrationStatus == SessionRegistrationStatus.APPROVED;
    }

    public boolean isCanceled() {
        return registrationStatus == SessionRegistrationStatus.CANCEL;
    }

    public SessionRegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public StudentSelectionStatus getSelectionStatus() {
        return selectionStatus;
    }
}
