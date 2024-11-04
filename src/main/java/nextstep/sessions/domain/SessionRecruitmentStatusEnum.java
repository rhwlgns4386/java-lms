package nextstep.sessions.domain;

import java.util.Arrays;

public enum SessionRecruitmentStatusEnum {
    NON_RECRUITING("00"),
    RECRUITING("01");

    private String statusCode ;

    SessionRecruitmentStatusEnum(String statusCode) {
        this.statusCode = statusCode;
    }

    public static SessionRecruitmentStatusEnum getByStatusCode(String recruitmentStatusCode) {
        return Arrays.stream(values()).filter(e -> e.statusCode.equals(recruitmentStatusCode)).findFirst().orElseThrow();
    }

    public String getStatusCode() {
        return statusCode;
    }
}


