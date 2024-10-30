package nextstep.courses.domain.cover;

public class CoverImage {
    private final CoverImageFile file;
    private final CoverImageType type;
    private final CoverImageSize imageSize;

    public CoverImage(CoverImageFile file, CoverImageType coverImageType, CoverImageSize coverImageSize) {
        this.file = file;
        this.type = coverImageType;
        this.imageSize = coverImageSize;
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
