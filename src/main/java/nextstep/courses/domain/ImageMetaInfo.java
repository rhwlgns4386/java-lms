package nextstep.courses.domain;

import java.util.Objects;

public class ImageMetaInfo {
    private final int byteSize;
    private final Extension extension;

    public ImageMetaInfo(int byteSize, Extension extension) {
        if (byteSize > 1) {
            throw new IllegalArgumentException();
        }
        this.byteSize = byteSize;
        this.extension = extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ImageMetaInfo))
            return false;
        ImageMetaInfo that = (ImageMetaInfo)o;
        return byteSize == that.byteSize && Objects.equals(extension, that.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byteSize, extension);
    }
}
