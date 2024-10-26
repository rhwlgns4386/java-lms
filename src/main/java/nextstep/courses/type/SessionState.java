package nextstep.courses.type;

public enum SessionState {
    PREPARING("강의 준비중"), OPEN("강의 모집중"), END("강의 종료");

    private final String desc;

    SessionState(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
