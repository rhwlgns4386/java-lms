package nextstep.courses.domain.cover;

public interface CoverImageRepository {
    int save(CoverImage coverImage);

    CoverImage findById(Long id);
}
