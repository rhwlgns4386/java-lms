package nextstep.courses.entity;

import nextstep.courses.domain.cover.CoverImage;

public class CoverImageEntity {

    private final Long id;
    private final String filePath;
    private final String sessionId;

    public CoverImageEntity(String filePath) {
        this(null, filePath, null);
    }

    public CoverImageEntity(Long id, String filePath) {
        this(id, filePath, null);
    }

    public CoverImageEntity(Long id, String filePath, String sessionId) {
        this.id = id;
        this.filePath = filePath;
        this.sessionId = sessionId;
    }

    public CoverImage toDomain() {
        return CoverImage.of(filePath);
    }

    public String getFilePath() {
        return filePath;
    }
}
