package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @DisplayName("강의 정보를 저장하고 id(pk)로 조회한다")
    @Test
    void save() {
        Session session = new Session(
                10000L,
                createSessionPeriod(),
                new Status(ProgressStatus.IN_PROGRESS, RecruitmentStatus.RECRUITING),
                null,
                new Pricing(PricingType.PAID, 10000),
                50,
                1L,
                LocalDateTime.now());

        sessionRepository.save(session);

        Session sessionById = sessionRepository.findById(1L).get();
        assertThat(sessionById).extracting("courseId", "dateRange", "pricing", "status", "availableSeats")
                .containsExactlyInAnyOrder(
                        10000L,
                        createSessionPeriod(),
                        new Pricing(PricingType.PAID, 10000),
                        SessionStatus.RECRUITING,
                        50
                );
    }

    private SessionPeriod createSessionPeriod() {
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 1, 0, 0);
        LocalDateTime endDate =LocalDateTime.of(2024, 10, 5, 0, 0);
        return new SessionPeriod(startDate, endDate);
    }
}

