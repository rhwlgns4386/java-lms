package nextstep.courses.domain.session;

public enum LegacySessionStatus {
    PREPARING,
    RECRUITING,
    CLOSE;

    public boolean isOpen() {
        return this == RECRUITING;
    }
}
