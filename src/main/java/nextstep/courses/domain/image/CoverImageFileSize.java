package nextstep.courses.domain.image;

import java.util.Objects;

public class CoverImageFileSize {
    private static final String OVER_LIMIT_FILE_SIZE_ERROR = "10MB 파일 크기를 초과하였습니다.";
    private static final int LIMIT_FILE_SIZE = 10 * 1024 * 1024;

    private final int size;

    public CoverImageFileSize(int size) {
        this.size = isSizeValid(size);
    }

    public int isSizeValid(int size) {
        if(size > LIMIT_FILE_SIZE) {
            throw new IllegalArgumentException(OVER_LIMIT_FILE_SIZE_ERROR);
        }
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoverImageFileSize that = (CoverImageFileSize) o;
        return size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }

}
