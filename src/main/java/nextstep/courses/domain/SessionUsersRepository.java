package nextstep.courses.domain;

import java.util.List;

public interface SessionUsersRepository {
    Long save(SessionStudent users);
    int updateBySelected(Long id, boolean isSelected);
    SessionStudent findById(Long id);
    SessionStudents findBySessionId(Long sessionId);
    void deleteBySessionId(Long sessionId);
    int[] bulkSave(List<SessionStudent> students);
}
