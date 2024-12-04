package nextstep.courses.domain;

public class CoverImage {
    private Long sessionId;
    private Long imageId;

    public CoverImage(Long sessionId, Long imageId) {
        this.sessionId = sessionId;
        this.imageId = imageId;
    }
}
