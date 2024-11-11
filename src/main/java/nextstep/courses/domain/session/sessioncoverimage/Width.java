package nextstep.courses.domain.session.sessioncoverimage;

public class Width {
    private static final int MIN_WIDTH = 300;

    private final int px;

    public Width(int px) {
        if (px < MIN_WIDTH) {
            throw new IllegalArgumentException("이미지의 가로 사이즈는 최소 300px 이상이어야 합니다.");
        }

        this.px = px;
    }

    public int getPx() {
        return this.px;
    }

    public int pxByRatio(int ratio) {
        return px * ratio;
    }
}
