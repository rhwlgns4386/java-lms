package nextstep.courses.domain;

import nextstep.courses.domain.session.SessionApply;

public interface SessionApplyRepository {
    int save(SessionApply sessionApply);

    SessionApply findById(Long id);

    void update(SessionApply apply);
}
