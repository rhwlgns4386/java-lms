package nextstep.courses.infrastructure;

import nextstep.courses.domain.cover.CoverImageRepository;
import nextstep.courses.domain.session.SessionRegistrationRepository;
import nextstep.courses.domain.session.SessionRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

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
                "INSERT INTO cover_image (id, file_size, image_type, width, height, created_at) VALUES (?, ?, ?, ?, ?, ?)",
                coverImageId,
                coverImage.getFileSize(),
                coverImage.getTypeName(),
                coverImage.getImageWidth(),
                coverImage.getImageHeight(),
                LocalDateTime.now()
        );
    }

    @DisplayName("무료 세션을 조회할 수 있다.")
    @Test
    void findFreeSession() {
        // given
        FreeSession freeSession = new FreeSession(SessionStatus.ready().recruiting(), period, coverImage);
        Long savedId = sessionRepository.saveFreeSession(freeSession);

        // when
        DefaultSession foundSession = sessionRepository.findById(savedId);

        // then
        FreeSession foundFreeSession = (FreeSession) foundSession;
        assertThat(foundFreeSession)
                .extracting("status.progress", "status.recruitment", "period.startDate", "period.endDate", "courseFee.amount", "capacity.maxStudents")
                .containsExactly(SessionProgress.READY, RecruitmentStatus.RECRUITING, period.getStartDate(), period.getEndDate(), 0L, Integer.MAX_VALUE);
    }

    @DisplayName("유료세션을 조회할 수 있다.")
    @Test
    void findPaidSession() {
        // given
        Money courseFee = new Money(50000L);
        PaidSession paidSession = new PaidSession(SessionStatus.ready().recruiting(), period, List.of(coverImage), courseFee, new Capacity(20));
        Long savedId = sessionRepository.savePaidSession(paidSession);

        // when
        DefaultSession foundSession = sessionRepository.findById(savedId);

        // then
        PaidSession foundPaidSession = (PaidSession) foundSession;
        assertThat(foundPaidSession)
                .extracting("status.progress", "status.recruitment", "period.startDate", "period.endDate", "courseFee.amount", "capacity.maxStudents")
                .containsExactly(SessionProgress.READY, RecruitmentStatus.RECRUITING, period.getStartDate(), period.getEndDate(), 50000L, 20);
    }

    @DisplayName("세션의 모든 커버 이미지를 조회할 수 있다")
    @Test
    void findSessionWithMultipleImages() {
        // given
        long secondImageId = 2L;
        CoverImage secondImage = new CoverImage(
                secondImageId,
                new CoverImageFile(1024 * 100),
                CoverImageType.JPG,
                new CoverImageSize(300, 200)
        );
        jdbcTemplate.update(
                "INSERT INTO cover_image (id, file_size, image_type, width, height, created_at) VALUES (?, ?, ?, ?, ?, ?)",
                secondImageId,
                secondImage.getFileSize(),
                secondImage.getTypeName(),
                secondImage.getImageWidth(),
                secondImage.getImageHeight(),
                LocalDateTime.now()
        );

        PaidSession paidSession = new PaidSession(SessionStatus.ready().recruiting(), period, List.of(coverImage, secondImage), new Money(50000L), new Capacity(20));
        Long savedId = sessionRepository.savePaidSession(paidSession);

        // when
        DefaultSession foundSession = sessionRepository.findById(savedId);

        // then
        assertAll(
                () -> assertThat(foundSession).isInstanceOf(PaidSession.class),
                () -> assertThat(foundSession.getCoverImages()).hasSize(2),
                () -> assertThat(foundSession.getCoverImages())
                        .extracting("file.size", "type", "imageSize.width", "imageSize.height")
                        .containsExactly(
                                tuple(coverImage.getFileSize(), CoverImageType.JPG, 600, 400),
                                tuple(1024 * 100, CoverImageType.JPG, 300, 200)
                        )
        );
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM session_registration");
        jdbcTemplate.update("DELETE FROM session");
        jdbcTemplate.update("DELETE FROM cover_image");
    }
}
