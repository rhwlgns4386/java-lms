package nextstep.courses.domain.coverimage;

import nextstep.courses.CoverImageException;

public class SessionCoverImage {
    private static final long MAX_STORAGE_CAPACITY = 1 * 1024 * 1024;

    private Long id;
    private Long sessionId;

    public SessionCoverImage(long storageCapacity, SessionCoverImagePath imagePath, SessionCoverImageSize imageSize) {
        validate(storageCapacity);
    }

    private void validate(long storageCapacity) {
        if (storageCapacity > MAX_STORAGE_CAPACITY) {
            throw new CoverImageException("1MB 용량을 초과했습니다.");
        }
    }

    public void save(Long sessionId) {
        this.sessionId = sessionId;
    }
}
