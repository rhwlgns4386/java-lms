package nextstep.courses.domain.session;

import nextstep.courses.domain.session.sessionCoverImage.SessionCoverImage;

public class SessionInfo {
    private final String title;
    private final SessionCoverImage sessionCoverImage;
    private final long creatorId;

    public SessionInfo(String title, SessionCoverImage sessionCoverImage, long creatorId) {
        this.title = title;
        this.sessionCoverImage = sessionCoverImage;
        this.creatorId = creatorId;
    }
}
