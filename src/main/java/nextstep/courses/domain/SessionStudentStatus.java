package nextstep.courses.domain;

public enum SessionStudentStatus {
    PASS,
    FAIL,
    REGISTERED,
    CANCELLED,
    ;

    public static boolean checkPass(SessionStudentStatus status) {
        return PASS.equals(status);
    }

    public static boolean checkFail(SessionStudentStatus status) {
        return FAIL.equals(status);
    }

    public static SessionStudentStatus from(String status) {
        return SessionStudentStatus.valueOf(status.toUpperCase());
    }
}
