package nextstep.courses.domain;

import java.util.Objects;

public class ImageSize {
    private static final long MAX_SIZE_IN_KB = 1024;

    private long size;

    public ImageSize(long size) {
        if (size > MAX_SIZE_IN_KB) {
            throw new IllegalArgumentException("Image size is too large");
        }
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageSize)) {
            return false;
        }
        ImageSize imageSize = (ImageSize) o;
        return getSize() == imageSize.getSize();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSize());
    }

    @Override
    public String toString() {
        return "ImageSize{" +
                "size=" + size +
                '}';
    }
}
