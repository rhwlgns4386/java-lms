package nextstep.courses.domain.session;

import nextstep.courses.domain.session.sessioncoverimage.SessionCoverImage;

import java.util.List;

public class SessionInfo {
    private final String title;
    private final List<SessionCoverImage> sessionCoverImages;
    private final long creatorId;

    public SessionInfo(String title, List<SessionCoverImage> sessionCoverImages, long creatorId) {
        this.title = title;
        this.sessionCoverImages = sessionCoverImages;
        this.creatorId = creatorId;
    }
}
