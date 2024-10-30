package nextstep.sessions.domain;

public class ImageHeight {
    private static int PROPORTION = 3;
    private static int MIN_HEIGHT = 200;
    private int height;

    public ImageHeight(int height) {
        if (height < MIN_HEIGHT) {
            throw new IllegalArgumentException("이미지 세로 픽셀 크기가 유효범위를 초과하였습니다");
        }
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getProportionHeight() {
        return PROPORTION * height;
    }
}
