package nextstep.courses.domain.session.sessioncoverimage;

public class SessionCoverImage {
    private final ImageType imageType;
    private final Ratio ratio;
    private final Size size;

    public SessionCoverImage(String imageFileType, Ratio ratio, Size size) {
        this.imageType = ImageType.findByFileType(imageFileType);
        this.ratio = ratio;
        this.size = size;
    }

}
