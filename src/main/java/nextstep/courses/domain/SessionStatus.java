package nextstep.courses.domain;

public enum SessionStatus {
    PREPARE,
    REGISTER,
    CLOSE,
    ;

    public static boolean isNotRegister(SessionStatus other) {
        return !(REGISTER == other);
    }

    public static SessionStatus from(String stringValue) {
        return SessionStatus.valueOf(stringValue.toUpperCase());
    }
}
