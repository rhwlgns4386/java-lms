package nextstep.courses.domain.SessionImage;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ImageSize {
    public static final int MINIMUM_WIDTH = 300;
    public static final int MINIMUM_HEIGHT = 200;
    public static final double EXPECTED_SIZE_PERCENT = 1.5;
    private final int width;
    private final int height;

    public ImageSize(int width, int height) {
        validateSize(width, height);
        this.width = width;
        this.height = height;
    }

    private void validateSize(int width, int height) {
        if (width < MINIMUM_WIDTH || height < MINIMUM_HEIGHT) {
            throw new IllegalArgumentException("이미지 사이즈 오류입니다.");
        }

        if (new BigDecimal(width / height)
                .setScale(1, RoundingMode.FLOOR).intValue() != EXPECTED_SIZE_PERCENT) {
            throw new IllegalArgumentException("이미지 비율 오류");
        }
    }
}
