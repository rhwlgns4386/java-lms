package nextstep.courses.domain;

public class SessionImageSize {
    private final int witdh;
    private final int height;

    public SessionImageSize(int width, int height) {
        validateImageSize(width, height);
        this.witdh = width;
        this.height = height;
    }

    private static void validateImageSize(int width, int height) {
        if (width < 300 || height < 200) {
            throw new IllegalArgumentException("이미지의 규격이 맞지 않습니다.");
        }
    }
}
