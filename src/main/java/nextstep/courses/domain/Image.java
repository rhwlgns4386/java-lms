package nextstep.courses.domain;

public class Image {
    private static final long MAX_SIZE_IN_KB = 1024;
    private static final long MIN_WIDTH = 300;
    private static final long MIN_HEIGHT = 200;
    private static final double RATIO = 3.0 / 2.0;

    private Long id;
    private Long imageSize;
    private ImageType imageType;
    private Long width;
    private Long height;

    public Image(Long imageSize, ImageType imageType, Long width, Long height) {
        this(null, imageSize, imageType, width, height);
    }

    public Image(Long id, Long imageSize, ImageType imageType, Long width, Long height) {
        validateImage(imageSize, width, height);
        this.id = id;
        this.imageSize = imageSize;
        this.imageType = imageType;
        this.width = width;
        this.height = height;
    }

    private static void validateImage(Long imageSize, Long width, Long height) {
        if (imageSize > MAX_SIZE_IN_KB) {
            throw new IllegalArgumentException("Image size must be less than 1000");
        }
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            throw new IllegalArgumentException("invalid width or height(width must be greater than 300, height must be greater than 200)");
        }
        if (((double) width / (double) height) != RATIO) {
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
