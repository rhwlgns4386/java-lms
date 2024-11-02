package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.SessionRegistrationEntity;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class SessionRegistrationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private JdbcSessionRegistrationRepository sessionRegistrationRepository;
    private Long sessionId;
    private Long userId;

    @BeforeEach
    void setUp() {
        sessionRegistrationRepository = new JdbcSessionRegistrationRepository(jdbcTemplate);

        userId = 4L;
        jdbcTemplate.update("INSERT INTO ns_user (id, user_id, password, name, email, created_at) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)",
                userId, "green_user", "password123", "Green", "green@example.com");

        jdbcTemplate.update("INSERT INTO cover_image (file_size, image_type, width, height, created_at) VALUES (102400, 'JPG', 600, 400, CURRENT_TIMESTAMP)");

        sessionId = 1L;
        jdbcTemplate.update("INSERT INTO session (id, status, start_date, end_date, cover_image_id, session_type) VALUES (?, 'OPEN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 'FREE')", sessionId);
    }

    @DisplayName("세션의 수강 신청 정보를 조회할 수 있다.")
    @Test
    void saveRegistrations() {
        // given
        List<SessionRegistrationEntity> registrations = List.of(
                new SessionRegistrationEntity(sessionId, userId, LocalDateTime.of(2024, 10, 10, 11, 0))
        );
        sessionRegistrationRepository.saveRegistrations(registrations);

        // when
        List<Long> savedUserIds = sessionRegistrationRepository.findRegisteredUserIds(sessionId);

        // then
        assertThat(savedUserIds).containsExactly(userId);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM session_registration");
        jdbcTemplate.update("DELETE FROM session");
        jdbcTemplate.update("DELETE FROM ns_user");
        jdbcTemplate.update("DELETE FROM cover_image");
    }
}
