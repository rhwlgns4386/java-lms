package nextstep.courses.domain;

import java.util.Objects;

public class Image {

    private final Long id;
    private final ImageSize imageSize;
    private final ImageMetaData metaInfo;

    public Image(ImageSize imageSize, ImageMetaData metaInfo) {
        this(0L, imageSize, metaInfo);
    }

    public Image(Long id, ImageSize imageSize, ImageMetaData metaInfo) {
        this.id = id;
        this.imageSize = imageSize;
        this.metaInfo = metaInfo;
    }

    public Long getId() {
        return id;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }

    public ImageMetaData getMetaInfo() {
        return metaInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Image)) {
            return false;
        }
        Image that = (Image)o;
        return Objects.equals(imageSize, that.imageSize) && Objects.equals(metaInfo, that.metaInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageSize, metaInfo);
    }
}
