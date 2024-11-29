package nextstep.courses;

public class ImageSizeMissException extends IllegalArgumentException {
    public ImageSizeMissException() {
        super("이미지의 크기가 잘못 되었습니다");
    }
}
