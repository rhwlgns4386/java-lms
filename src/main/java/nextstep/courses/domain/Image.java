package nextstep.courses.domain;

public class Image {
    private Long id;
    private Long imageSize;
    private ImageType imageType;
    private Long width;
    private Long height;

    public Image(Long id, Long imageSize, ImageType imageType, Long width, Long height) {
        this.id = id;
        this.imageSize = imageSize;
        this.imageType = imageType;
        this.width = width;
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public Long getImageSize() {
        return imageSize;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public Long getWidth() {
        return width;
    }

    public Long getHeight() {
        return height;
    }
}
