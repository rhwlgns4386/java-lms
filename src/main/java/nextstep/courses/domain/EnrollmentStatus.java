package nextstep.courses.domain;

public enum EnrollmentStatus {
    NOT_RECRUITING,
    RECRUITING;

    public static EnrollmentStatus findBySessionStatus(SessionStatus sessionStatus) {
        if (isRecruiting(sessionStatus)) {
            return RECRUITING;
        }
        return NOT_RECRUITING;
    }

    private static boolean isRecruiting(SessionStatus sessionStatus) {
        return sessionStatus == SessionStatus.PROGRESS;
    }

    public boolean isRecruiting() {
        return this == RECRUITING;
    }
}
