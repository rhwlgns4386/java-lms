package nextstep.courses.domain.coverimage;

import nextstep.courses.CoverImageException;

public class SessionCoverImage {
    private static final long MAX_STORAGE_CAPACITY = 1 * 1024 * 1024;

    private Long id;
    private Long sessionId;
    private SessionCoverImageSize sessionCoverImageSize;
    private SessionCoverImagePath sessionCoverImagePath;

    public SessionCoverImage(Long id, Long sessionId, SessionCoverImagePath imagePath, SessionCoverImageSize imageSize) {
        this.id = id;
        this.sessionId = sessionId;
        this.sessionCoverImagePath = imagePath;
        this.sessionCoverImageSize = imageSize;
    }

    public static SessionCoverImage create(Long sessionId, Long storageCapacity, SessionCoverImagePath imagePath, SessionCoverImageSize imageSize) {
        validate(storageCapacity);
        return new SessionCoverImage(0L, sessionId, imagePath, imageSize);
    }


    private static void validate(long storageCapacity) {
        if (storageCapacity > MAX_STORAGE_CAPACITY) {
            throw new CoverImageException("1MB 용량을 초과했습니다.");
        }
    }

    public Long getSessionId() {
        return sessionId;
    }

    public SessionCoverImagePath getSessionCoverImagePath() {
        return sessionCoverImagePath;
    }

    public SessionCoverImageSize getSessionCoverImageSize() {
        return sessionCoverImageSize;
    }
}
