package nextstep.courses.domain.image;

public interface ImageRepository {
    int save(Image image, Long sessionId);

    Image findById(long id);
}
