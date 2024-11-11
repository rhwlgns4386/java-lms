package nextstep.courses.domain.session.sessioncoverimage;

public class Ratio {
    private static final int WIDTH_RATIO = 3;
    private static final int HEIGHT_RATIO = 2;

    private final Width width;
    private final Height height;

    public Ratio(Width width, Height height) {
        if (width.pxByRatio(HEIGHT_RATIO) != height.pxByRatio(WIDTH_RATIO)) {
            throw new IllegalArgumentException("이미지의 가로, 세로의 비율은 3:2여야 합니다.");
        }

        this.width = width;
        this.height = height;
    }
}
