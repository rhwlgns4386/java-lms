package nextstep.sessions.domain;

import nextstep.global.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Image extends BaseEntity {
    private static final int MAX_IMAGE_SIZE_IN_KB = 1000;
    private static final int MIN_WIDTH_HEIGHT_IN_PIXELS = 200;
    private static final int WIDTH_OF_WIDTH_TO_HEIGHT_RATIO = 3;
    private static final int HEIGHT_OF_WIDTH_TO_HEIGHT_RATIO = 2;

    private ImageType type;
    private int sizeInKB;
    private int widthInPixels;
    private int heightInPixels;

    public Image(int sizeInKB, String type, int widthInPixels, int heightInPixels, LocalDateTime createdAt) {
        this(0L, sizeInKB, type, widthInPixels, heightInPixels, createdAt, null);
    }

    public Image(Long id, int sizeInKB, String type, int widthInPixels, int heightInPixels, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        validateImageSize(sizeInKB);
        validateWidthHeight(widthInPixels, heightInPixels);
        this.type = ImageType.of(type);
        this.sizeInKB = sizeInKB;
        this.widthInPixels = widthInPixels;
        this.heightInPixels = heightInPixels;
    }

    private void validateImageSize(int sizeInKB) {
        if (sizeInKB > MAX_IMAGE_SIZE_IN_KB) {
            throw new IllegalArgumentException("이미지 크기는 1MB 이하여야 합니다.");
        }
    }

    private void validateWidthHeight(int widthInPixels, int heightInPixels) {
        if (isNonAllowedPixels(widthInPixels, heightInPixels)) {
            throw new IllegalArgumentException(String.format("width와 height는 200 픽셀 이상이어야 합니다.(width: %dpx, height: %dpx)", widthInPixels, heightInPixels));
        }
        if (isNonAllowedRatio(widthInPixels, heightInPixels)) {
            throw new IllegalArgumentException(String.format("width와 height의 비율은 3:2여야 합니다.(width: %dpx, height: %dpx)", widthInPixels, heightInPixels));
        }
    }

    private static boolean isNonAllowedPixels(int widthInPixels, int heightInPixels) {
        return widthInPixels < MIN_WIDTH_HEIGHT_IN_PIXELS || heightInPixels < MIN_WIDTH_HEIGHT_IN_PIXELS;
    }

    private boolean isNonAllowedRatio(int widthInPixels, int heightInPixels) {
        return widthInPixels * HEIGHT_OF_WIDTH_TO_HEIGHT_RATIO != heightInPixels * WIDTH_OF_WIDTH_TO_HEIGHT_RATIO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return sizeInKB == image.sizeInKB && widthInPixels == image.widthInPixels && heightInPixels == image.heightInPixels && type == image.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, sizeInKB, widthInPixels, heightInPixels);
    }
}
