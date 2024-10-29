package nextstep.courses.domain.cover;

public class CoverImageFile {
    private static final int ONE_MEGA_BYTE = 1024 * 1024;
    private final int size;

    public CoverImageFile(int size) {
        if (size > ONE_MEGA_BYTE) {
            throw new IllegalArgumentException("이미지 사이즈는 1MB이하입니다.");
        }
        this.size = size;
    }
}
