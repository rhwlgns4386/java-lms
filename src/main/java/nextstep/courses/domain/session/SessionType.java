package nextstep.courses.domain.session;

public enum SessionType {
    FREE("free"),
    PAID("paid");

    private final String code;

    SessionType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
