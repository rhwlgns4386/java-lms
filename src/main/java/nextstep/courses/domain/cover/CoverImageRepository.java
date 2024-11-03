package nextstep.courses.domain.cover;

public interface CoverImageRepository {
    Long save(CoverImage coverImage);

    CoverImage findById(Long id);
}
