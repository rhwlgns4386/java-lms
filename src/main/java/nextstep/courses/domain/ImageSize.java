package nextstep.courses.domain;

public class ImageSize {
    private static long MAX_IMAGE_SIZE = 1024 * 1024;

    private long size;

    public ImageSize(long size) {
        if (size > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("최대 이미지 사이즈를 초과 했습니다.");
        }
        this.size = size;
    }
}
