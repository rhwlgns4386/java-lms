package nextstep.courses.domain;

public interface ImageRepository {
    int save(Image image, Long sessionId);

    Image findById(long id);
}
