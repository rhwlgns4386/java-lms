package nextstep.courses.domain.image;

public class SessionImage {
    private final String fileName;
    private final ImageSize imageSize;
    private final ImageExtension imageExtension;

    public SessionImage(String fileName, int width, int height, int volume) {
        this.fileName = fileName;
        this.imageSize = new ImageSize(width, height, volume);
        this.imageExtension = ImageExtension.from(fileName);
    }
}
