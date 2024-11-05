package nextstep.courses.infrastructure.cover;

import nextstep.courses.entity.CoverImageEntity;

import java.util.List;

public interface CoverImageRepository {

    int save(CoverImageEntity coverImageEntity, long sessionId);

    List<CoverImageEntity> findBySessionId(long sessionId);
}
