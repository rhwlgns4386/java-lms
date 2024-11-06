package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.SessionRegistration;
import nextstep.courses.domain.session.SessionRegistrationRepository;
import nextstep.courses.domain.session.SessionRegistrationStatus;
import nextstep.courses.domain.session.StudentSelectionStatus;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class SessionRegistrationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRegistrationRepository sessionRegistration;
    private Long sessionId;

    @BeforeEach
    void setUp() {
        sessionRegistration = new JdbcSessionRegistrationRepository(jdbcTemplate);
        sessionId = 1L;
    }

    @DisplayName("수강신청시 초기 상태는 PENDING이다")
    @Test
    void saveInitialRegistration() {
        // given
        Long userId = NsUserTest.GREEN.getId();

        // when
        sessionRegistration.saveRegistrations(sessionId, List.of(userId));

        // then
        Optional<SessionRegistration> found = sessionRegistration.findBySessionIdAndUserId(sessionId, userId);
        assertThat(found).isPresent();
        assertAll(
                () -> assertThat(found.get().getRegistrationStatus()).isEqualTo(SessionRegistrationStatus.PENDING),
                () -> assertThat(found.get().getSelectionStatus()).isEqualTo(StudentSelectionStatus.PENDING)
        );
    }
    @DisplayName("수강신청을 선택하여 승인할 수 있다.")
    @Test
    void updateRegistrationStatus() {
        // given
        Long userId = NsUserTest.GREEN.getId();
        sessionRegistration.saveRegistrations(sessionId, List.of(userId));
        SessionRegistration registration = sessionRegistration.findBySessionIdAndUserId(sessionId, userId).get();

        // when
        registration.select();
        registration.approve();
        sessionRegistration.update(registration);

        // then
        SessionRegistration updated = sessionRegistration.findBySessionIdAndUserId(sessionId, userId).get();
        assertAll(
                () -> assertThat(updated.getSelectionStatus()).isEqualTo(StudentSelectionStatus.SELECTED),
                () -> assertThat(updated.getRegistrationStatus()).isEqualTo(SessionRegistrationStatus.APPROVED)
        );
    }

    @DisplayName("승인된 수강신청만 조회할 수 있다")
    @Test
    void findOnlyApprovedRegistrations() {
        // given
        Long userId1 = NsUserTest.GREEN.getId();
        Long userId2 = NsUserTest.JAVAJIGI.getId();
        sessionRegistration.saveRegistrations(sessionId, List.of(userId1, userId2));

        SessionRegistration registration1 = sessionRegistration.findBySessionIdAndUserId(sessionId, userId1).get();
        registration1.select();
        registration1.approve();
        sessionRegistration.update(registration1);

        // when
        List<SessionRegistration> approvedUsers = sessionRegistration.findRegisteredUsers(sessionId);

        // then
        assertThat(approvedUsers)
                .hasSize(1);
    }
    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM session_registration");
        jdbcTemplate.update("DELETE FROM session");
        jdbcTemplate.update("DELETE FROM ns_user");
        jdbcTemplate.update("DELETE FROM cover_image");
    }
}
