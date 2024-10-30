package nextstep.sessions.domain;

public class ImageWidth {
    private static int PROPORTION = 2;
    private static int MIN_WIDTH = 300;

    private int width;

    public ImageWidth(int width) {
        if (width < MIN_WIDTH) {
            throw new IllegalArgumentException("이미지 가로 픽셀 크기가 유효범위를 초과하였습니다.");
        }
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public int getProportionWidth() {
        return PROPORTION * width;
    }
}
