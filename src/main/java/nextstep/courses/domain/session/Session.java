package nextstep.courses.domain.session;

import nextstep.courses.domain.image.SessionCoverImage;

public class FreeSession {
    private final Long sessionId;
    private final EnrollUserInfos enrollUserInfos;
    private final SessionType sessionType;
    private final SessionStatus sessionStatus;
    private final SessionCoverImage sessionCoverImage;
    private final SessionDate sessionDate;

    public FreeSession(Long sessionId, EnrollUserInfos enrollUserInfos, SessionType sessionType, SessionStatus sessionStatus, SessionCoverImage sessionCoverImage, SessionDate sessionDate) {
        this.sessionId = sessionId;
        this.enrollUserInfos = enrollUserInfos;
        this.sessionType = sessionType;
        this.sessionStatus = sessionStatus;
        this.sessionCoverImage = sessionCoverImage;
        this.sessionDate = sessionDate;
    }


}
