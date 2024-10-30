package nextstep.sessions.domain;

public class SessionImage {
    private static long MAX_IMAGE_SIZE = 1024 *1024;
    private String url;
    private ImageSize imageSize;
    private SessionImagePixel imagePixel;
    private SessionImageTypeEnum imageType;


    public SessionImage(String url, ImageSize imageSize , SessionImageTypeEnum imageType, SessionImagePixel sessionImagePixel) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("이미지 경로가 누락되었습니다.");
        }
        if (imageSize == null) {
            throw new IllegalArgumentException("이미지가 비어 있습니다.");
        }
        if (imageType == null) {
            throw new IllegalArgumentException("이미지 타입이 누락되었습니다");
        }
        if (sessionImagePixel == null) {
            throw new IllegalArgumentException("이미지 픽셀이 누락 되었습니다");
        }
        this.url = url;
        this.imageSize = imageSize;
        this.imageType = imageType;
        this.imagePixel = sessionImagePixel;
    }
}
