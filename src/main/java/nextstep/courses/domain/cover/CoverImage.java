package nextstep.courses.domain.cover;

public class CoverImage {
    private final Long id;
    private final CoverImageFile file;
    private final CoverImageType type;
    private final CoverImageSize imageSize;

    public CoverImage(CoverImageFile file, CoverImageType type, CoverImageSize imageSize) {
        this(null, file, type, imageSize);
    }

    public CoverImage(Long id, CoverImageFile file, CoverImageType coverImageType, CoverImageSize coverImageSize) {
        this.id = id;
        this.file = file;
        this.type = coverImageType;
        this.imageSize = coverImageSize;
    }

    public Long getId() {
        return id;
    }

    public int getFileSize() {
        return file.getSize();
    }

    public CoverImageType getType() {
        return type;
    }
    public String getTypeName() {
        return type.getCode();
    }

    public int getImageWidth() {
        return imageSize.getWidth();
    }

    public int getImageHeight() {
        return imageSize.getHeight();
    }
}
