package nextstep.courses.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import nextstep.courses.domain.EnrollmentStudent;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.domain.RequestStatus;
import nextstep.courses.domain.SessionStatus;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class JdbcEnrollmentRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcEnrollmentRepository jdbcEnrollmentRepository;

    @BeforeEach
    void setUp() {
        jdbcEnrollmentRepository = new JdbcEnrollmentRepository(
                new JdbcEnrollmentStudentDao(jdbcTemplate));
    }

    @Test
    void findBySessionId() {
        Set<EnrollmentStudent> enrollmentStudents = jdbcEnrollmentRepository.findBySessionId(1L);
        assertThat(enrollmentStudents).isEqualTo(Set.of(new EnrollmentStudent(1, 1, RequestStatus.PENDING),
                new EnrollmentStudent(1, 2, RequestStatus.ACCEPTED)));
    }

    @Test
    void findByPendingStudentSessionId() {
        Set<EnrollmentStudent> enrollmentStudents = jdbcEnrollmentRepository.findByPendingStudentSessionId(
                1L);
        assertThat(enrollmentStudents).isEqualTo(Set.of(new EnrollmentStudent(1, 1, RequestStatus.PENDING)));
    }

    @Test
    void update() {
        Set<EnrollmentStudent> enrollmentStudents = jdbcEnrollmentRepository.findByPendingStudentSessionId(
                1L);
        Enrollments enrollments = new Enrollments(SessionStatus.PROGRESS, enrollmentStudents);
        enrollments.accept(NsUserTest.JAVAJIGI);

        jdbcEnrollmentRepository.update(enrollments);
        assertThat(jdbcEnrollmentRepository.findByPendingStudentSessionId(1L)).isEmpty();

        Set<EnrollmentStudent> result = jdbcEnrollmentRepository.findBySessionId(1L);
        assertThat(result).isEqualTo(
                Set.of(new EnrollmentStudent(1, 1, RequestStatus.ACCEPTED),
                        new EnrollmentStudent(1, 2, RequestStatus.ACCEPTED)));
    }
}
