package nextstep.sessions.domain;

public class ApplicationStatus {
    private ApplicationStatusType applicationStatusEnum;


    public ApplicationStatus(String applicationStatus) {
        this(ApplicationStatusType.getByValue(applicationStatus));
    }

    public ApplicationStatus(ApplicationStatusType applicationStatusEnum) {
        if (applicationStatusEnum == null) {
            throw new RuntimeException("신청상태 값이 누락되었습니다.");
        }
        this.applicationStatusEnum = applicationStatusEnum;
    }

    public ApplicationStatus approve() {
        if (isApplied()) {
            return new ApplicationStatus(ApplicationStatusType.APPROVE);
        }
        throw new RuntimeException("승인 가능한 상태가 아닙니다");
    }


    public ApplicationStatus cancel() {
        if (isApplied()) {
            return new ApplicationStatus(ApplicationStatusType.CANCEL);
        }
        throw new RuntimeException("취소가능한 상태가 아닙니다");
    }

    public boolean isApplied() {
        return this.applicationStatusEnum == ApplicationStatusType.APPLY;
    }


    public boolean isValidStatusToAttend() {
        return this.applicationStatusEnum == ApplicationStatusType.APPROVE;
    }

    public ApplicationStatusType getApplicationStatusEnum() {
        return applicationStatusEnum;
    }
}
