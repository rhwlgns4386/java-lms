package nextstep.courses.domain;

import nextstep.util.NullValidator;

public class Image {
    private Long id;
    private final String fileName;
    private final ImageSize imageSize;
    private final ImageType imageType;

    public Image(String fileName, int width, int height, int size, ImageType imageType) {
        this(fileName, new ImageSize(width, height, size), imageType);
    }

    public Image(String fileName, ImageSize imageSize, ImageType imageType) {
        this(null, fileName, imageSize, imageType);
    }

    public Image(Long id, String fileName, ImageSize imageSize, ImageType imageType) {
        NullValidator.validateNull(fileName, imageSize, imageType);
        this.id = id;
        this.fileName = fileName;
        this.imageSize = imageSize;
        this.imageType = imageType;
    }

    public String fileName() {
        return fileName;
    }

    public int width() {
        return imageSize.width();
    }

    public int height() {
        return imageSize.height();
    }

    public int bytes() {
        return imageSize.bytes();
    }

    public ImageType type() {
        return imageType;
    }

    public Long id() {
        return id;
    }
}
