package nextstep.courses.type;

public enum RecruitState {

    RECRUIT, NOT_RECRUIT;

    public boolean canRegister() {
        return this == RECRUIT;
    }
}
