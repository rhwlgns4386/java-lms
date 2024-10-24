package nextstep.courses.domain;

public class SessionImage {
    private final int volume;
    private final ImageType type;
    private final ImageSize imageSize;

    public SessionImage(int volume, ImageType type, ImageSize imageSize) {
        if (volume > 1024) {
            throw new IllegalArgumentException("이미지의 용량은 1MB를 초과할 수 없습니다.");
        }

        this.volume = volume;
        this.type = type;
        this.imageSize = imageSize;
    }
}
