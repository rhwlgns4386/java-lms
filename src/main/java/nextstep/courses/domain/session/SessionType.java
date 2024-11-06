package nextstep.courses.domain.session;

public enum SessionType {
    FREE("free", "무료강의"),
    PAID("paid", "유료강의");

    private final String code;
    private final String description;

    SessionType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }
}
