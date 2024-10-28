package nextstep.courses.domain.SessionImage;

public class SessionImage {

    private final ImageCapacity imageCapacity;
    private final ImageType imageType;
    private final ImageSize imageSize;


    public SessionImage(int imageCapacity, String imageType, int width, int height) {
        this.imageCapacity = new ImageCapacity(imageCapacity);
        this.imageType = new ImageType(imageType);
        this.imageSize = new ImageSize(width, height);



    }
}
