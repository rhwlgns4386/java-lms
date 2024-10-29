package nextstep.courses.domain;

public class SessionImagePixel {
    private ImageWidth width ;
    private ImageHeight height;

    public SessionImagePixel(ImageWidth width, ImageHeight height) {
        if (width.getProportionWidth() != height.getProportionHeight()) {
            throw new IllegalArgumentException("이미지 가로 , 세로 픽셀 비율은 3:2여야 합니다");
        }
        this.width = width;
        this.height = height;
    }
}
