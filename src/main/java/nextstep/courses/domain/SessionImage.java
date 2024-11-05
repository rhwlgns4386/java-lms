package nextstep.courses.domain;


public class SessionImage {
    private int size;

    private ImageType imageType;

    private int width;

    private int height;

    public SessionImage(int size, String imageType, int width, int height) {
        this.size = size;

        if (size > 1 * 1024 * 1024) {
            throw new IllegalArgumentException("이미지 크기를 초과했습니다.");
        }

        if (width < 300 || height < 200 || width / height != 3 / 2) {
            throw new IllegalArgumentException("유효하지 않은 이미지 사이즈입니다.");
        }

        this.imageType = ImageType.fromString(imageType);
        this.width = width;
        this.height = height;
    }
}
