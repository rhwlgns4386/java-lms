package nextstep.courses.type;

public enum EnrollmentState {
    APPLY("신청중"), APPROVE("승인"), REJECT("수강신청 불가");

    private final String desc;

    EnrollmentState(String desc) {
        this.desc = desc;
    }

    public boolean canChange() {
        return this == APPLY;
    }

    public EnrollmentState approve() {
        validateState();
        return APPROVE;
    }

    private void validateState() {
        if (this != APPLY) {
            throw new IllegalStateException(desc + " 상태에서는 수강 신청 승인할 수 없습니다");
        }
    }

    public EnrollmentState reject() {
        validateState();
        return REJECT;
    }

    public boolean isRegistered() {
        return this == APPROVE || this == APPLY;
    }

    public String getDesc() {
        return desc;
    }
}
