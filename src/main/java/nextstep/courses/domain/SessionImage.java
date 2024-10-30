package nextstep.courses.domain;

import java.util.Objects;

public class SessionImage {

    private final ImageSize imageSize;
    private final ImageMetaData metaInfo;

    public SessionImage(ImageSize imageSize, ImageMetaData metaInfo) {
        this.imageSize = imageSize;
        this.metaInfo = metaInfo;
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
        if (!(o instanceof SessionImage)) {
            return false;
        }
        SessionImage that = (SessionImage)o;
        return Objects.equals(imageSize, that.imageSize) && Objects.equals(metaInfo, that.metaInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageSize, metaInfo);
    }
}
