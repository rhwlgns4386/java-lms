package nextstep.courses.infrastructure;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.EnrollmentStudentRepository;
import nextstep.courses.domain.Enrollments;
import nextstep.util.Sets;

public class JdbcEnrollmentRepositoryAdapter implements EnrollmentStudentRepository {

    private final JdbcEnrollmentStudentDao jdbcEnrollmentStudentDao;

    public JdbcEnrollmentRepositoryAdapter(JdbcEnrollmentStudentDao jdbcEnrollmentStudentDao) {
        this.jdbcEnrollmentStudentDao = jdbcEnrollmentStudentDao;
    }

    @Override
    public Set<EnrollmentStudent> findBySessionId(long sessionId) {
        return jdbcEnrollmentStudentDao.findAllEnrollmentStudentBySessionId(sessionId);
    }

    @Override
    public void update(Enrollments enrollments) {
        Set<EnrollmentStudent> enrollmentStudents = enrollments.enrolledStudents();
        jdbcEnrollmentStudentDao.insertItem(newEnrolledStudents(enrollmentStudents, sessionId(enrollmentStudents)));
    }

    private long sessionId(Set<EnrollmentStudent> students) {
        List<Long> sessionIds = toIds(students);

        if (sessionIds.size() != 1) {
            throw new IllegalStateException("");
        }

        return sessionIds.get(0);  // 유일한 sessionId 반환
    }

    private List<Long> toIds(Set<EnrollmentStudent> students) {
        return students.stream()
                .map(EnrollmentStudent::getSessionId)
                .distinct()  // 중복된 sessionId를 제거
                .collect(Collectors.toList());
    }

    private Set<EnrollmentStudent> newEnrolledStudents(Set<EnrollmentStudent> newItem, long id) {
        Set<EnrollmentStudent> before = jdbcEnrollmentStudentDao.findAllEnrollmentStudentBySessionId(id);
        return Sets.difference(newItem, before);
    }
}
