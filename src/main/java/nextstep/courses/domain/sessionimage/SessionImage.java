package nextstep.courses.domain.sessionimage;

public class SessionImage {

    private final ImageCapacity capacity;
    private final ImageType type;
    private final ImageSize size;


    public SessionImage(ImageCapacity capacity, ImageType type, ImageSize size) {
        ImageType.validateType(type.name());
        this.capacity = capacity;
        this.type = type;
        this.size = size;
    }
}
