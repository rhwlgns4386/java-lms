package nextstep.courses.domain;

public enum SessionStatus {
    PREPARE,
    REGISTER,
    PROGRESS,
    CLOSE,
    ;

    public static boolean isNotRegister(SessionStatus other) {
        return !(REGISTER == other);
    }

    public static SessionStatus from(String stringValue) {
        return SessionStatus.valueOf(stringValue.toUpperCase());
    }

    public static boolean cannotRegister(SessionStatus status) {
        return CLOSE == status;
    }
}
