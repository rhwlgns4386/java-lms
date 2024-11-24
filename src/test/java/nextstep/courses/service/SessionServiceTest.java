package nextstep.courses.service;

import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.StudentRepository;
import nextstep.courses.domain.session.enrollment.ApprovalStatus;
import nextstep.courses.domain.session.entity.StudentEntity;
import nextstep.courses.domain.session.sessioncoverimage.SessionCoverImageRepository;
import nextstep.courses.infrastructure.JdbcSessionCoverImageRepository;
import nextstep.courses.infrastructure.JdbcSessionRepository;
import nextstep.courses.infrastructure.JdbcStudentRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import nextstep.users.domain.UserRepository;
import nextstep.users.infrastructure.JdbcUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@ExtendWith(MockitoExtension.class)
@JdbcTest
class SessionServiceTest {

    private SessionRepository sessionRepository;

    private StudentRepository studentRepository;

    private UserRepository userRepository;

    private SessionCoverImageRepository sessionCoverImageRepository;

    private SessionService sessionService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private NsUser newStudent;
    private Payment payment;

    @BeforeEach
    void setUp() {
        userRepository = new JdbcUserRepository(jdbcTemplate);
        studentRepository = new JdbcStudentRepository(jdbcTemplate);
        sessionCoverImageRepository = new JdbcSessionCoverImageRepository(jdbcTemplate);
        sessionRepository = new JdbcSessionRepository(jdbcTemplate, sessionCoverImageRepository, userRepository);

        sessionService = new SessionService(sessionRepository, studentRepository);

        newStudent = NsUserTest.NEW_USER;
        payment = new Payment("4", 2L, 4L, 800_000L);
    }

    @Test
    void enroll_수강신청_성공() {
        sessionService.enroll(2L, newStudent, payment);

        List<StudentEntity> students = studentRepository.findBySessionId(2L);
        Assertions.assertThat(students.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("현재 모집중인 강의가 아닌 경우 수강신청 실패")
    void enroll_수강신청_실패() {
        Assertions.assertThatThrownBy(() -> {
            sessionService.enroll(1L, newStudent, payment);
        });
    }

    @Test
    @DisplayName("선발된 수강생에 대해 수강 승인 처리")
    void approve_수강승인_성공() {
        List<Long> userIds = List.of(1L, 2L);

        sessionService.approve(1L, userIds);

        List<StudentEntity> approvedStudents = studentRepository.findByIdAndSessionId(1L, userIds);

        Assertions.assertThat(ApprovalStatus.isApproved(approvedStudents.get(0).getApprovalStatus())).isTrue();
        Assertions.assertThat(ApprovalStatus.isApproved(approvedStudents.get(1).getApprovalStatus())).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 수강생인 경우 수강 승인 처리 실패")
    void approve_수강승인_실패() {
        List<Long> userIds = List.of(1L, 2L, 3L, 4L);

        Assertions.assertThatThrownBy(() -> {
            sessionService.approve(1L, userIds);
        }).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void disapprove() {
        long userId = 1L;
        sessionService.disapprove(1L, userId);

        List<StudentEntity> disapproved = studentRepository.findByIdAndSessionId(1L, List.of(userId));

        Assertions.assertThat(ApprovalStatus.isDisapproved(disapproved.get(0).getApprovalStatus())).isTrue();
    }
}