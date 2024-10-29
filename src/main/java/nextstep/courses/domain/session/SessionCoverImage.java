package nextstep.courses.domain.session;

import nextstep.courses.domain.image.CoverImageFileSize;

public class SessionCoverImage {

    private final CoverImageFileSize coverImageFileSize;

    public SessionCoverImage(int fileSize) {
        this(new CoverImageFileSize(fileSize));
    }

    public SessionCoverImage(CoverImageFileSize coverImageFileSize) {
        this.coverImageFileSize = coverImageFileSize;
    }


}
