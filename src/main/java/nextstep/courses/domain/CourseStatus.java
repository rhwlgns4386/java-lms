package nextstep.courses.domain;

public enum CourseStatus {
    READY("ready"),
    OPEN("open"),
    CLOSED("closed");

    private String code;

    CourseStatus(String code) {
        this.code = code;
    }

    public boolean isOpen() {
        return this == OPEN;
    }


}
