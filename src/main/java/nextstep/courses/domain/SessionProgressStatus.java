package nextstep.courses.domain;

public enum SessionProgressStatus {
    PREPARING, PROGRESSING, CLOSED;

    public boolean isProgressing() {
        return this == PROGRESSING;
    }

    public boolean isPreparing() {
        return this == PREPARING;
    }

    public boolean isClosed() {
        return this == CLOSED;
    }
}
