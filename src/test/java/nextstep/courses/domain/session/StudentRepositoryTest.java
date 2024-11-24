package nextstep.courses.domain.session;

import nextstep.courses.domain.session.enrollment.ApprovalStatus;
import nextstep.courses.domain.session.entity.StudentEntity;
import nextstep.courses.infrastructure.JdbcStudentRepository;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@JdbcTest
class StudentRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository = new JdbcStudentRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        NsUser newStudent = NsUserTest.NEW_USER;

        studentRepository.save(newStudent, 1L);

        List<StudentEntity> students = studentRepository.findBySessionId(1L);
        Assertions.assertThat(students.size()).isEqualTo(3);
    }

    @Test
    void findByIdAndSessionId() {
        List<Long> userIds = List.of(1L, 2L);

        List<StudentEntity> selected = studentRepository.findByIdAndSessionId(1L, userIds);

        Assertions.assertThat(selected.size()).isEqualTo(userIds.size());
    }

    @Test
    void update_approved() {
        List<Long> userIds = List.of(1L, 2L);

        int approvedStudents = studentRepository.updateApproved(1L, userIds);
        Assertions.assertThat(approvedStudents).isEqualTo(userIds.size());
    }

    @Test
    void update_disapproved() {
        long userId = 1L;
        studentRepository.updateDisapproved(1L, userId);

        List<StudentEntity> disapprovedStudents = studentRepository.findByIdAndSessionId(1L, List.of(userId));

        Assertions.assertThat(ApprovalStatus.isDisapproved(disapprovedStudents.get(0).getApprovalStatus())).isTrue();
    }
}