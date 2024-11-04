package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionImage;

import java.util.List;

public interface SessionImageRepository {
    int save(SessionImage sessionImage);

    int save(SessionImage sessionImage, Long sessionId);

    SessionImage findById(Long id);

    List<SessionImage> findBySessionId(Long sessionId);

}
