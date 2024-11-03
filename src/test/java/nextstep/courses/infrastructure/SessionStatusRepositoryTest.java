package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class SessionStatusRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionStatusRepository sessionStatusRepository;

    @BeforeEach
    void setUp() {
        sessionStatusRepository = new JdbcSessionStatusRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("세션의 상태를 조회할 수 있다.")
    void find() {
        // given
        Long sessionId = 1L;
        SessionStatus status = new SessionStatus(SessionProgress.IN_PROGRESS, RecruitmentStatus.RECRUITING);
        sessionStatusRepository.save(sessionId, status);

        // when
        SessionStatus foundStatus = sessionStatusRepository.findBySessionId(sessionId);

        // then
        assertThat(foundStatus)
                .extracting("progress", "recruitment")
                .containsExactly(SessionProgress.IN_PROGRESS, RecruitmentStatus.RECRUITING);
    }

    @Test
    @DisplayName("세션 상태로 수강신청 가능 여부를 판단할 수 있다")
    void canRegister() {
        // given
        Long sessionId = 1L;
        SessionStatus recruitingStatus = new SessionStatus(SessionProgress.READY, RecruitmentStatus.RECRUITING);
        sessionStatusRepository.save(sessionId, recruitingStatus);

        // when
        SessionStatus foundStatus = sessionStatusRepository.findBySessionId(sessionId);

        // then
        assertThat(foundStatus.canRegister()).isTrue();
    }

    @DisplayName("존재하지 않는 세션 ID로 조회시 예외가 발생한다")
    @Test
    void findByNotExistSessionId() {
        assertThatThrownBy(() -> sessionStatusRepository.findBySessionId(999L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM session_status");
    }
}
