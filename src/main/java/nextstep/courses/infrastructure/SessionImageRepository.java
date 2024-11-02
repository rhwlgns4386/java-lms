package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionImage;

public interface SessionImageRepository {
    int save(SessionImage sessionImage);
    SessionImage findById(Long id);

}
