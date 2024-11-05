package nextstep.sessions.domain;

import java.util.Arrays;

public enum SessionRecruitmentStatusType {
    NON_RECRUITING("00"),
    RECRUITING("01");

    private String statusCode ;

    SessionRecruitmentStatusType(String statusCode) {
        this.statusCode = statusCode;
    }

    public static SessionRecruitmentStatusType getByStatusCode(String recruitmentStatusCode) {
        return Arrays.stream(values()).filter(e -> e.statusCode.equals(recruitmentStatusCode)).findFirst().orElseThrow();
    }

    public String getStatusCode() {
        return statusCode;
    }
}


