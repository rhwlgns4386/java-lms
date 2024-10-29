package nextstep.courses.domain.SessionImage;

public class SessionImage {

    private final ImageCapacity imageCapacity;
    private final ImageType imageType;
    private final ImageSize imageSize;


    public SessionImage(ImageCapacity imageCapacity, ImageType imageType, ImageSize imageSize) {
        ImageType.validateType(imageType.name());
        this.imageCapacity = imageCapacity;
        this.imageType = imageType;
        this.imageSize = imageSize;
    }
}
