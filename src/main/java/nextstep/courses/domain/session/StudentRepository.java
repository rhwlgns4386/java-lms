package nextstep.courses.domain.session;

import nextstep.courses.domain.session.entity.StudentEntity;
import nextstep.users.domain.NsUser;

import java.util.List;

public interface StudentRepository {
    int save(NsUser student, long sessionId);

    List<StudentEntity> findBySessionId(long sessionId);

    List<StudentEntity> findByIdAndSessionId(long sessionId, List<Long> userIds);

    int updateApproved(long sessionId, List<Long> userIdList);

    int updateDisapproved(long sessionId, long userId);
}
