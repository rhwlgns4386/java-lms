package nextstep.courses.service;

import nextstep.courses.entity.SessionEntity;
import nextstep.courses.entity.SessionEntityTest;
import nextstep.courses.infrastructure.cover.JdbcCoverImageRepository;
import nextstep.courses.infrastructure.enrollment.JdbcStudentRepository;
import nextstep.courses.infrastructure.enrollment.StudentRepository;
import nextstep.courses.infrastructure.session.JdbcSessionRepository;
import nextstep.courses.infrastructure.session.SessionRepository;
import nextstep.courses.type.EnrollmentState;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import nextstep.users.infrastructure.JdbcUserRepository;
import nextstep.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcOperations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

@JdbcTest
public class SessionServiceTest {

    @Autowired
    private JdbcOperations jdbcOperations;

    private SessionService sessionService;
    private StudentRepository studentRepository;

    @BeforeEach
    void init() {
        sessionService = new SessionService(new JdbcSessionRepository(jdbcOperations, new JdbcCoverImageRepository(jdbcOperations)),
                new JdbcStudentRepository(jdbcOperations), new UserService(new JdbcUserRepository(jdbcOperations)));
        studentRepository = new JdbcStudentRepository(jdbcOperations);
    }

    @Test
    void throw_exception_if_apply_not_recruit_session() {
        assertThatIllegalStateException().isThrownBy(() -> sessionService.apply(NsUserTest.JAVAJIGI.getUserId(),
                1L, new Payment("테스트", 1L, NsUserTest.JAVAJIGI.getId(), 0L)));
    }

    @Test
    void throw_exception_if_apply_not_invalid_session_id() {
        assertThatIllegalStateException().isThrownBy(() -> sessionService.apply(NsUserTest.JAVAJIGI.getUserId(),
                2L, new Payment("테스트", 1L, NsUserTest.JAVAJIGI.getId(), 10000L)));
    }

    @Test
    void throw_exception_if_apply_invalid_session_fee() {
        assertThatIllegalStateException().isThrownBy(() -> sessionService.apply(NsUserTest.JAVAJIGI.getUserId(),
                2L, new Payment("테스트", 2L, NsUserTest.JAVAJIGI.getId(), 0L)));
    }

    @Test
    void test_register_with_db() {
        NsUser user = new NsUser(3L, "test");
        Payment payment = new Payment("test", 2L, 3L, 10000L);

        sessionService.apply(user.getUserId(), 2L, payment);
        sessionService.register(user.getUserId(), 2L);

        EnrollmentState enrollmentState = studentRepository.findBySessionId(2L)
                .stream()
                .filter(entity -> entity.getStudent().matchUser(user))
                .findFirst()
                .orElseThrow()
                .getEnrollmentState();

        assertThat(enrollmentState).isEqualTo(EnrollmentState.APPROVE);
    }

    @Test
    void reject_with_db() {
        Payment payment = new Payment("test", 2L, NsUserTest.JAVAJIGI.getId(), 10000L);

        sessionService.apply(NsUserTest.JAVAJIGI.getUserId(), 2L, payment);
        sessionService.reject(NsUserTest.JAVAJIGI.getUserId(), 2L);

        EnrollmentState enrollmentState = studentRepository.findBySessionId(2L)
                .stream()
                .filter(entity -> entity.getStudent().matchUser(NsUserTest.JAVAJIGI))
                .findFirst()
                .orElseThrow()
                .getEnrollmentState();

        assertThat(enrollmentState).isEqualTo(EnrollmentState.REJECT);
    }
}
