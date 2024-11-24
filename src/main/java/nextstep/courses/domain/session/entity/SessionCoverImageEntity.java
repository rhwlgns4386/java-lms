package nextstep.courses.domain.session.entity;

import nextstep.courses.domain.session.sessioncoverimage.Height;
import nextstep.courses.domain.session.sessioncoverimage.Ratio;
import nextstep.courses.domain.session.sessioncoverimage.SessionCoverImage;
import nextstep.courses.domain.session.sessioncoverimage.Size;
import nextstep.courses.domain.session.sessioncoverimage.Width;

public class SessionCoverImageEntity {
    private final long id;
    private final long session_id;
    private final String imageType;
    private final int width;
    private final int height;
    private final long size;

    public SessionCoverImageEntity(long id, long session_id, String imageType, int width, int height, long size) {
        this.id = id;
        this.session_id = session_id;
        this.imageType = imageType;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    public SessionCoverImage toSessionCoverImage() {
        return new SessionCoverImage(imageType.toLowerCase(),
                                     new Ratio(new Width(width), new Height(height)),
                                     new Size(size));
    }
}
