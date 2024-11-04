package nextstep.sessions.domain;

public class ApplicationStatus {
    private ApplicationStatusEnum applicationStatusEnum;


    public ApplicationStatus(ApplicationStatusEnum applicationStatusEnum) {
        this.applicationStatusEnum = applicationStatusEnum;
    }

    public ApplicationStatus approve() {
        if (isApplied()) {
            return new ApplicationStatus(ApplicationStatusEnum.approve);
        }
        throw new RuntimeException("승인 가능한 상태가 아닙니다");
    }


    public ApplicationStatus cancel() {
        if (isApplied()) {
            return new ApplicationStatus(ApplicationStatusEnum.cancel);
        }
        throw new RuntimeException("취소가능한 상태가 아닙니다");
    }

    public boolean isApplied() {
        return this.applicationStatusEnum == ApplicationStatusEnum.apply;
    }


    public boolean isValidStatusToAttend() {
        return this.applicationStatusEnum == ApplicationStatusEnum.approve;
    }

    public ApplicationStatusEnum getApplicationStatusEnum() {
        return applicationStatusEnum;
    }
}
