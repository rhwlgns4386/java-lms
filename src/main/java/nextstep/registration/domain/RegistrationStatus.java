package nextstep.registration.domain;

public enum RegistrationStatus {
    PENDING("대기"),
    APPROVED("승인"),
    REJECTED("거절");

    private String state;

    RegistrationStatus(String state) {
        this.state = state;
    }

    public boolean isApproved() {
        return this == APPROVED;
    }

}
