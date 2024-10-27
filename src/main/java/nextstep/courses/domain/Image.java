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
        if (imageSize > 1000) {
            throw new IllegalArgumentException("Image size must be less than 1000");
        }
        if (((double) width / (double) height) != (3.0 / 2.0)) {
            throw new IllegalArgumentException("width:height ratio must be 3:2");
        }
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
