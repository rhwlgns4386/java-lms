package nextstep.courses.infrastructure;

import nextstep.courses.domain.CoverImageRepository;
import nextstep.courses.domain.SessionRegistrationRepository;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImageFile;
import nextstep.courses.domain.cover.CoverImageSize;
import nextstep.courses.domain.cover.CoverImageType;
import nextstep.courses.domain.session.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;
    private SessionRegistrationRepository sessionRegistrationRepository;
    private CoverImageRepository coverImageRepository;

    private CoverImage coverImage;
    private Period period;
    private Long coverImageId;

    @BeforeEach
    void setUp() {
        sessionRegistrationRepository = new JdbcSessionRegistrationRepository(jdbcTemplate);
        coverImageRepository = new JdbcCoverImageRepository(jdbcTemplate);
        sessionRepository = new JdbcSessionRepository(jdbcTemplate, sessionRegistrationRepository, coverImageRepository);

        period = new Period(LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 19));

        coverImageId = 1L;
        coverImage = new CoverImage(
                coverImageId,
                new CoverImageFile(1024 * 100),
                CoverImageType.JPG,
                new CoverImageSize(600, 400)
        );

        jdbcTemplate.update(
                "insert into cover_image (id, file_size, image_type, width, height, created_at) values (?, ?, ?, ?, ?, ?)",
                coverImageId,
                coverImage.getFile().getSize(),
                coverImage.getType().name(),
                coverImage.getImageSize().getWidth(),
                coverImage.getImageSize().getHeight(),
                LocalDateTime.now()
        );
    }


    @DisplayName("무료 세션을 조회할 수 있다.")
    @Test
    void findFreeSession() {
        // given
        FreeSession freeSession = new FreeSession(Status.OPEN, period, coverImage);
        Long savedId = sessionRepository.saveFreeSession(freeSession);

        // when
        DefaultSession foundSession = sessionRepository.findById(savedId);

        // then
        assertThat(foundSession).isInstanceOf(FreeSession.class);
        FreeSession foundFreeSession = (FreeSession) foundSession;
        assertThat(foundFreeSession)
                .extracting("status", "period.startDate", "period.endDate", "courseFee", "capacity.maxStudents")
                .containsExactly(Status.OPEN, period.getStartDate(), period.getEndDate(), new Money(0), Integer.MAX_VALUE);
    }

    @DisplayName("유료세션을 조회할 수 있다.")
    @Test
    void findPaidSession() {
        // given
        Money courseFee = new Money(50000L);
        PaidSession paidSession = new PaidSession(Status.OPEN, period, coverImage, courseFee, new Capacity(20));
        Long savedId = sessionRepository.savePaidSession(paidSession);

        // when
        DefaultSession foundSession = sessionRepository.findById(savedId);

        // then
        assertThat(foundSession).isInstanceOf(PaidSession.class);
        PaidSession foundPaidSession = (PaidSession) foundSession;
        assertThat(foundPaidSession)
                .extracting("status", "period.startDate", "period.endDate", "courseFee", "capacity.maxStudents")
                .containsExactly(Status.OPEN, period.getStartDate(), period.getEndDate(), courseFee, 20);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("delete from session_registration");
        jdbcTemplate.update("delete from session");
        jdbcTemplate.update("delete from cover_image");
    }
}
