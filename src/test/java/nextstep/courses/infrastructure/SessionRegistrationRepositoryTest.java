package nextstep.courses.infrastructure;

import nextstep.courses.domain.CoverImageRepository;
import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImageFile;
import nextstep.courses.domain.cover.CoverImageSize;
import nextstep.courses.domain.cover.CoverImageType;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class SessionRegistrationRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRegistrationRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private JdbcSessionRegistrationRepository sessionRegistrationRepository;
    private Long sessionId;

    @BeforeEach
    void setUp() {
        sessionRegistrationRepository = new JdbcSessionRegistrationRepository(jdbcTemplate);

        sessionId = 1L;
    }

    @DisplayName("세션의 수간신청 정보를 조회할 수 있다.")
    @Test
    void saveRegistrations() {
        // given
        List<SessionRegistrationEntity> registrations = List.of(
                new SessionRegistrationEntity(sessionId, NsUserTest.GREEN.getId(), LocalDateTime.of(2024, 10, 10, 11, 0))
        );
        sessionRegistrationRepository.saveRegistrations(registrations);

        // when
        List<Long> savedUserIds = sessionRegistrationRepository.findRegisteredUserIds(sessionId);

        // then
        assertThat(savedUserIds).containsExactly(NsUserTest.GREEN.getId());
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("delete from session_registration");
        jdbcTemplate.update("delete from session");
        jdbcTemplate.update("delete from ns_user");
    }
}
