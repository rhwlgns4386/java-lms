package nextstep.courses.domain;

public enum SessionState {
    READY, START, END;

    public static boolean isRequestSession(SessionState sessionState) {
        if (READY.equals(sessionState) || END.equals(sessionState)) {
            return false;
        }
        return START.equals(sessionState);
    }
}
