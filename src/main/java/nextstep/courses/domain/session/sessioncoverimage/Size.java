package nextstep.courses.domain.session.sessioncoverimage;

public class Size {
    private static final long MAX_SIZE = 1 * 1024 * 1024;
    private final long bytes;

    public Size(long bytes) {
        if (bytes > MAX_SIZE) {
            throw new IllegalArgumentException("이미지 크기는 1MB 이하여야 합니다.");
        }

        this.bytes = bytes;
    }

    public long getBytes() {
        return this.bytes;
    }
}
