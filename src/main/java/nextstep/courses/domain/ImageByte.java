package nextstep.courses.domain;

import java.util.Objects;
import nextstep.courses.ImageSizeMissException;

public class ImageByte {
    private static final Positive MAX_BYTE_SIZE = new Positive(1024 * 1024);
    private final Positive byteSize;

    public ImageByte(int byteSize) {
        this(new Positive(byteSize));
    }

    public ImageByte(Positive byteSize) {
        Objects.requireNonNull(byteSize);
        validateOverMaxSize(byteSize);
        this.byteSize = byteSize;
    }

    private static void validateOverMaxSize(Positive byteSize) {
        if (MAX_BYTE_SIZE.isLessThan(byteSize)) {
            throw new ImageSizeMissException();
        }
    }
}
