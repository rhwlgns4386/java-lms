package nextstep.courses.domain.session;

public enum SessionRecruitStatus {
    NON_RECRUITMENT("비모집중"),
    RECRUITMENT("모집중");

    private final String value;

    SessionRecruitStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
