package nextstep.courses.domain.session.sessionCoverImage;

public class Height {
    private static final int MIN_HEIGHT = 200;

    private final int px;

    public Height(int px) {
        if (px < MIN_HEIGHT) {
            throw new IllegalArgumentException("이미지의 세로 사이즈는 최소 200px 이상이어야 합니다.");
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
