package nextstep.courses.domain;

import java.util.Objects;

public class SessionImage {


    public static final int MAX_IMAGE_SIZE = 1000000;
    public static final String IMAGE_SIZE_EXCEPT_MESSAGE = "이미지는 1MB 이하 이어야 합니다!";
    public static final String WIDTH_HEIGHT_EXCEPT_MESSAGE = "width 는 300 픽셀 이상, height 은 200 픽셀 이상, 비율은 3:2 이어야 합니다!";
    public static final int MIN_WIDTH = 300;
    public static final int MIN_HEIGHT = 200;
    public static final double WIDTH_HEIGHT_RATE = 1.5;

    private final int size;
    private final SessionImageType type;
    private final int width;
    private final int height;


    public SessionImage(int size, String type, int width, int height) {
        this(size, SessionImageType.of(type), width, height);
    }

    public SessionImage(int size, SessionImageType type, int width, int height) {
        if (size > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException(IMAGE_SIZE_EXCEPT_MESSAGE);
        }
        if (width < MIN_WIDTH || height < MIN_HEIGHT || (double) width / height != WIDTH_HEIGHT_RATE) {
            throw new IllegalArgumentException(WIDTH_HEIGHT_EXCEPT_MESSAGE);
        }
        this.size = size;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SessionImage)) return false;
        SessionImage that = (SessionImage) o;
        return size == that.size && width == that.width && height == that.height && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, type, width, height);
    }
}
