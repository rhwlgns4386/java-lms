package nextstep.courses.domain;

public enum SessionType {
    FREE("free"),
    PAID("paid");

    private String code;

    SessionType(String code) {
        this.code = code;
    }
}
