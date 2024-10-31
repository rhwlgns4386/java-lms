package nextstep.courses.domain.cover;

public class CoverImage {
    private final Long id;
    private final CoverImageFile file;
    private final CoverImageType type;
    private final CoverImageSize imageSize;

    public CoverImage(Long id, CoverImageFile file, CoverImageType coverImageType, CoverImageSize coverImageSize) {
        this.id = id;
        this.file = file;
        this.type = coverImageType;
        this.imageSize = coverImageSize;
    }

    public Long getId() {
        return id;
    }

    public CoverImageFile getFile() {
        return file;
    }

    public CoverImageType getType() {
        return type;
    }

    public CoverImageSize getImageSize() {
        return imageSize;
    }
}
