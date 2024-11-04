package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.List;

public interface SessionStudentRepository {
    void registerStudentToSession(Long sessionId, NsUser student);

    List<Long> getRegisteredStudentIds(Long sessionId);
}
