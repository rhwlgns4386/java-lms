package nextstep.courses.entity;

import nextstep.courses.domain.cover.CoverImage;

public class CoverImageEntity {

    private final Long id;
    private final String filePath;

    public CoverImageEntity(String filePath) {
        this(null, filePath);
    }

    public CoverImageEntity(Long id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }

    public CoverImage toDomain() {
        return CoverImage.of(filePath);
    }

    public String getFilePath() {
        return filePath;
    }
}
