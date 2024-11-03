package nextstep.courses.domain;

public enum RegisterStatus {
    NOTREGISTER, REGISTER;

    public static boolean isNotRegister(RegisterStatus status) {
        return NOTREGISTER == status;
    }

    public static RegisterStatus from(String stringValue) {
        return RegisterStatus.valueOf(stringValue.toUpperCase());
    }
}
