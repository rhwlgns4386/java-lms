package nextstep.courses.domain.coverimage;

import nextstep.qna.CoverImageException;

public class SessionCoverImage {
    private static final long MAX_STORAGE_CAPACITY = 1 * 1024 * 1024;

    public SessionCoverImage(long storageCapacity, SessionCoverImagePath imagePath, SessionCoverImageSize imageSize) {
        validate(storageCapacity);
        mapping(imagePath, imageSize);
    }

    private void validate(long storageCapacity) {
        if (storageCapacity > MAX_STORAGE_CAPACITY) {
            throw new CoverImageException("1MB 용량을 초과했습니다.");
        }
    }

    private void mapping(SessionCoverImagePath imagePath, SessionCoverImageSize imageSize) {
        imagePath.mapping(this);
        imageSize.mapping(this);
    }
}
