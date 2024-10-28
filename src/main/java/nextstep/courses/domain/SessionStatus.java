package nextstep.courses.domain;

public enum SessionStatus {
    READY("ready"),
    OPEN("open"),
    CLOSED("closed");

    private String code;

    SessionStatus(String code) {
        this.code = code;
    }

    public boolean isOpen() {
        return this == OPEN;
    }


}
