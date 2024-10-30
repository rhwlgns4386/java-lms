package nextstep.courses.domain;

import java.util.Objects;

public class ImageMetaData {
    public static final int byteMB = 1;
    private final int byteSize;
    private final Extension extension;

    public ImageMetaData(int byteSize, Extension extension) {
        if (byteSize > byteMB) {
            throw new IllegalArgumentException();
        }
        this.byteSize = byteSize;
        this.extension = extension;
    }

    public int getByteSize() {
        return byteSize;
    }

    public Extension getExtension() {
        return extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageMetaData)) {
            return false;
        }
        ImageMetaData that = (ImageMetaData)o;
        return byteSize == that.byteSize && Objects.equals(extension, that.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byteSize, extension);
    }
}
