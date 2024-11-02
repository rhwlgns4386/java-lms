package nextstep.courses.type;

public enum SessionState {
    PREPARING("강의 준비중"), PROGRESS("강의 진행중"), END("강의 종료");

    private final String desc;

    SessionState(String desc) {
        this.desc = desc;
    }

    public boolean canRegister() {
        return !this.equals(SessionState.END);
    }

    public String getDesc() {
        return desc;
    }
}
