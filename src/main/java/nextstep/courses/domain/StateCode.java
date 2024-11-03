package nextstep.courses.domain;

public enum StateCode {
    READY(10),
    RECRUITING(20),
    END(30);

    private final int statusCode;

    StateCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void validateOrderSessionStatus() {
        if (StateCode.RECRUITING != this) {
            throw new IllegalArgumentException("모집하지 않는 강의입니다.");
        }
    }

}
