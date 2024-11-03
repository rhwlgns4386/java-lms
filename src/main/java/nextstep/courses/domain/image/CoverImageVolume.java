package nextstep.courses.domain.image;

import java.util.Objects;

public class CoverImageVolume {
    private static final String OVER_LIMIT_FILE_SIZE_ERROR = "1MB 파일 크기를 초과하였습니다.";
    private static final int LIMIT_FILE_SIZE = 1024;

    private final int size;

    public CoverImageVolume(int size) {
        this.size = isSizeValid(size);
    }

    public int isSizeValid(int size) {
        if(size > LIMIT_FILE_SIZE) {
            throw new IllegalArgumentException(OVER_LIMIT_FILE_SIZE_ERROR);
        }
        return size;
    }

    public int getSize() {
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
        CoverImageVolume that = (CoverImageVolume) o;
        return size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }

}
