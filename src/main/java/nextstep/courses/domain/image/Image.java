package nextstep.courses.domain.image;

import java.util.Objects;

public class Image {
    private Long id;
    private ImageSize imageSize;
    private ImageType imageType;
    private ImagePixel imagePixel;

    public Image(ImageSize imageSize, ImageType imageType, ImagePixel imagePixel) {
        this(null, imageSize, imageType, imagePixel);
    }

    public Image(Long id, ImageSize imageSize, ImageType imageType, ImagePixel imagePixel) {
        this.id = id;
        this.imageSize = imageSize;
        this.imageType = imageType;
        this.imagePixel = imagePixel;
    }

    public Long getId() {
        return id;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public ImagePixel getImagePixel() {
        return imagePixel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        Image image = (Image) o;
        return Objects.equals(getId(), image.getId()) && Objects.equals(getImageSize(), image.getImageSize()) && getImageType() == image.getImageType() && Objects.equals(getImagePixel(), image.getImagePixel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getImageSize(), getImageType(), getImagePixel());
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", imageSize=" + imageSize +
                ", imageType=" + imageType +
                ", imagePixel=" + imagePixel +
                '}';
    }
}
