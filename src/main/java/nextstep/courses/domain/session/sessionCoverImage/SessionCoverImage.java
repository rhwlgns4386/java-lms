package nextstep.courses.domain.session.sessionCoverImage;

public class SessionCoverImage {
    private final String imageFileType;
    private final Ratio ratio;
    private final Size size;

    public SessionCoverImage(String imageFileType, Ratio ratio, Size size) {
        ImageType.isValid(imageFileType);

        this.imageFileType = imageFileType;
        this.ratio = ratio;
        this.size = size;
    }

}
